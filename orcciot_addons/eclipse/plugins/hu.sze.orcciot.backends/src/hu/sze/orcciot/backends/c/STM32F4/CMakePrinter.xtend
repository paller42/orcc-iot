package hu.sze.orcciot.backends.c.STM32F4

import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Network
import java.util.Map
import java.util.Properties
import java.util.List

import static net.sf.orcc.backends.BackendsConstants.*

/**
 * Generate Makefile content for the stm32f4xx build
 * 
 * @author Gabor Paller
 */
class CMakePrinter extends CommonPrinter {

	protected var Network network
	protected boolean linkNativeLib;
	protected String linkNativeLibFolder;
    String stm32f4_fwpath
    String stm32f4_newlibpath
	String stm32f4_native_lib_dir
	String stm32f4_native_lib_name

	def setNetwork(Network network) {
		this.network = network
	}

	override setOptions(Map<String, Object> options) {
		super.setOptions(options)

		if(options.containsKey(LINK_NATIVE_LIBRARY)) {
			linkNativeLib = options.get(LINK_NATIVE_LIBRARY) as Boolean;
			linkNativeLibFolder = options.get(LINK_NATIVE_LIBRARY_FOLDER) as String;
		}

		if(linkNativeLib && linkNativeLibFolder != "")
			linkNativeLib = true
		else
			linkNativeLib = false
	} 

	def setStm32f4Properties(Properties stm32f4Props) {
		stm32f4_fwpath = stm32f4Props.getProperty( "stm32f4.fw.path" )
		stm32f4_newlibpath = stm32f4Props.getProperty( "stm32f4.newlib.path" )
		stm32f4_native_lib_dir = stm32f4Props.getProperty( "stm32f4.native.lib.dir" )
		stm32f4_native_lib_name = stm32f4Props.getProperty( "stm32f4.native.lib.name" )
	}
	    
	def rootMakefileContent() '''
# Generated from «network.simpleName»
PROJ_NAME=«network.simpleName»

STM32=libs/stm32f4
STM32SRC=$(STM32)/src
RT=libs/orcc-runtime
RTSRC=$(RT)/src

SRCS = src/«network.simpleName».c
«FOR child : network.children.actorInstances.filter[!getActor.native]»
SRCS += src/«child.label».c
«ENDFOR»
«FOR child : network.children.filter(typeof(Actor)).filter[!native]»
SRCS += src/«child.label».c
«ENDFOR»
«FOR child : network.outputs»
SRCS += src/«child.name».c
«ENDFOR»
«FOR child : network.inputs»
SRCS += src/«child.name».c
«ENDFOR»
SRCS += $(STM32SRC)/newlib_stubs.c $(STM32SRC)/system_stm32f4xx.c $(STM32SRC)/hwsupport.c $(STM32SRC)/modem.c
SRCS += $(RTSRC)/dataflow.c $(RTSRC)/graph.c $(RTSRC)/options.c $(RTSRC)/rdtsc.c \
	$(RTSRC)/util.c $(RTSRC)/profiling.c $(RTSRC)/trace.c $(RTSRC)/scheduler.c $(RTSRC)/mapping.c $(RTSRC)/strutil.c

STM_COMMON=«stm32f4_fwpath»
NEWLIB=«stm32f4_newlibpath»

SRCS += $(STM_COMMON)/Libraries/STM32F4xx_StdPeriph_Driver/src/stm32f4xx_usart.c
SRCS += $(STM_COMMON)/Libraries/STM32F4xx_StdPeriph_Driver/src/stm32f4xx_rcc.c
SRCS += $(STM_COMMON)/Libraries/STM32F4xx_StdPeriph_Driver/src/stm32f4xx_gpio.c
SRCS += $(STM_COMMON)/Libraries/STM32F4xx_StdPeriph_Driver/src/stm32f4xx_tim.c
SRCS += $(STM_COMMON)/Libraries/STM32F4xx_StdPeriph_Driver/src/misc.c

CC=arm-none-eabi-gcc
OBJCOPY=arm-none-eabi-objcopy

CFLAGS = -Ilibs  -I$(RT)/include -I$(STM32)/include -Ilibs/orcc-native/include
CFLAGS += -g -O2 -Wall -Tstm32_flash.ld 
CFLAGS += -mlittle-endian -mthumb -mcpu=cortex-m4 -mthumb-interwork
CFLAGS += -mfloat-abi=hard -mfpu=fpv4-sp-d16 -D__FPU_PRESENT
CFLAGS += -Wno-unused-function -Wno-misleading-indentation -Wno-unused-label -Wno-unused-variable -Wno-unused-but-set-variable
CFLAGS += -DUSE_STDPERIPH_DRIVER

«IF stm32f4_native_lib_dir != null»
# Include files for the native library
CFLAGS += -I«stm32f4_native_lib_dir»/public-include
«ENDIF»

# Include files from STM libraries
CFLAGS += -I$(STM_COMMON)/Utilities/STM32F4-Discovery
CFLAGS += -I$(STM_COMMON)/Libraries/CMSIS/Include -I$(STM_COMMON)/Libraries/CMSIS/ST/STM32F4xx/Include
CFLAGS += -I$(STM_COMMON)/Libraries/STM32F4xx_StdPeriph_Driver/inc
CFLAGS += -I$(NEWLIB)/libc/include
LDFLAGS = -L$(NEWLIB) -lc
«IF stm32f4_native_lib_dir != null && stm32f4_native_lib_name!=null»
LDFLAGS += -L«stm32f4_native_lib_dir» -l«stm32f4_native_lib_name»
«ENDIF»

# add startup file to build
SRCS += $(STM_COMMON)/Libraries/CMSIS/ST/STM32F4xx/Source/Templates/TrueSTUDIO/startup_stm32f4xx.s 
OBJS = $(SRCS:.c=.o)


.PHONY: proj

all: proj

proj: $(PROJ_NAME).elf

$(PROJ_NAME).elf: $(SRCS)
	$(CC) $(CFLAGS) $^ -o $@ $(LDFLAGS)
	$(OBJCOPY) -O ihex $(PROJ_NAME).elf $(PROJ_NAME).hex
	$(OBJCOPY) -O binary $(PROJ_NAME).elf $(PROJ_NAME).bin

clean:
	rm -f *.o $(PROJ_NAME).elf $(PROJ_NAME).hex $(PROJ_NAME).bin

# Flash the STM32F4
burn: proj
	st-flash write $(PROJ_NAME).bin 0x8000000
	'''
	
	def rootLinkerScriptContent() '''
/* Linker script for STM32F407VG Device with 1024KByte FLASH, 192KByte RAM */

/* Entry Point */
ENTRY(Reset_Handler)

/* Highest address of the user mode stack */
_estack = 0x20020000;    /* end of 128K RAM on AHB bus*/

/* Generate a link error if heap and stack don't fit into RAM */
_Min_Heap_Size = 0;      /* required amount of heap  */
_Min_Stack_Size = 0x400; /* required amount of stack */

/* Specify the memory areas */
MEMORY
{
  FLASH (rx)      : ORIGIN = 0x08000000, LENGTH = 1024K
  RAM (xrw)       : ORIGIN = 0x20000000, LENGTH = 192K
  MEMORY_B1 (rx)  : ORIGIN = 0x60000000, LENGTH = 0K
}

/* Define output sections */
SECTIONS
{
  /* The startup code goes first into FLASH */
  .isr_vector :
  {
    . = ALIGN(4);
    KEEP(*(.isr_vector)) /* Startup code */
    . = ALIGN(4);
  } >FLASH

  /* The program code and other data goes into FLASH */
  .text :
  {
    . = ALIGN(4);
    *(.text)           /* .text sections (code) */
    *(.text*)          /* .text* sections (code) */
    *(.rodata)         /* .rodata sections (constants, strings, etc.) */
    *(.rodata*)        /* .rodata* sections (constants, strings, etc.) */
    *(.glue_7)         /* glue arm to thumb code */
    *(.glue_7t)        /* glue thumb to arm code */
	*(.eh_frame)

    KEEP (*(.init))
    KEEP (*(.fini))

    . = ALIGN(4);
    _etext = .;        /* define a global symbols at end of code */
    _exit = .;
  } >FLASH


   .ARM.extab   : { *(.ARM.extab* .gnu.linkonce.armextab.*) } >FLASH
    .ARM : {
    __exidx_start = .;
      *(.ARM.exidx*)
      __exidx_end = .;
    } >FLASH

  .preinit_array     :
  {
    PROVIDE_HIDDEN (__preinit_array_start = .);
    KEEP (*(.preinit_array*))
    PROVIDE_HIDDEN (__preinit_array_end = .);
  } >FLASH
  .init_array :
  {
    PROVIDE_HIDDEN (__init_array_start = .);
    KEEP (*(SORT(.init_array.*)))
    KEEP (*(.init_array*))
    PROVIDE_HIDDEN (__init_array_end = .);
  } >FLASH
  .fini_array :
  {
    PROVIDE_HIDDEN (__fini_array_start = .);
    KEEP (*(.fini_array*))
    KEEP (*(SORT(.fini_array.*)))
    PROVIDE_HIDDEN (__fini_array_end = .);
  } >FLASH

  /* used by the startup to initialize data */
  _sidata = .;

  /* Initialized data sections goes into RAM, load LMA copy after code */
  .data : AT ( _sidata )
  {
    . = ALIGN(4);
    _sdata = .;        /* create a global symbol at data start */
    *(.data)           /* .data sections */
    *(.data*)          /* .data* sections */

    . = ALIGN(4);
    _edata = .;        /* define a global symbol at data end */
  } >RAM

  /* Uninitialized data section */
  . = ALIGN(4);
  .bss :
  {
    /* This is used by the startup in order to initialize the .bss secion */
    _sbss = .;         /* define a global symbol at bss start */
    __bss_start__ = _sbss;
    *(.bss)
    *(.bss*)
    *(COMMON)

    . = ALIGN(4);
    _ebss = .;         /* define a global symbol at bss end */
    __bss_end__ = _ebss;
  } >RAM

  /* User_heap_stack section, used to check that there is enough RAM left */
  ._user_heap_stack :
  {
    . = ALIGN(4);
    PROVIDE ( end = . );
    PROVIDE ( _end = . );
    . = . + _Min_Heap_Size;
    . = . + _Min_Stack_Size;
    . = ALIGN(4);
  } >RAM

  /* MEMORY_bank1 section, code must be located here explicitly            */
  /* Example: extern int foo(void) __attribute__ ((section (".mb1text"))); */
  .memory_b1_text :
  {
    *(.mb1text)        /* .mb1text sections (code) */
    *(.mb1text*)       /* .mb1text* sections (code)  */
    *(.mb1rodata)      /* read-only data (constants) */
    *(.mb1rodata*)
  } >MEMORY_B1

  /* Remove information from the standard libraries */
  /DISCARD/ :
  {
    libc.a ( * )
    libm.a ( * )
    libgcc.a ( * )
  }

  .ARM.attributes 0 : { *(.ARM.attributes) }
}
	'''

}

