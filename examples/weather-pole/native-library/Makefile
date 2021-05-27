# Binaries will be generated with this name (.elf, .bin, .hex, etc)
PROJ_NAME=native-actors

# Put your stlink folder here so make burn will work.
STLINK=

# Put your source files here (or *.c, etc)
SRCS = src/native-actors.c src/i2c.c

# Put your STM32F4 library code directory here
STM_COMMON=/home/paller/stm32/stm32f4xx/STM32F4-Discovery_FW_V1.1.0
NEWLIB=/home/paller/stm32/stm32f4xx/newlib-cygwin/newlib

SRCS += $(STM_COMMON)/Libraries/STM32F4xx_StdPeriph_Driver/src/stm32f4xx_i2c.c

# Normally you shouldn't need to change anything below this line!
#######################################################################################

CC=arm-none-eabi-gcc
AR=arm-none-eabi-ar

CFLAGS = -Iinclude -Ipublic-include
CFLAGS += -g -O2 -Wall 
CFLAGS += -mlittle-endian -mthumb -mcpu=cortex-m4 -mthumb-interwork
CFLAGS += -mfloat-abi=hard -mfpu=fpv4-sp-d16 -D__FPU_PRESENT
CFLAGS += -Wno-unused-function -Wno-misleading-indentation
CFLAGS += -DUSE_STDPERIPH_DRIVER

# Include files from STM libraries
CFLAGS += -I$(STM_COMMON)/Utilities/STM32F4-Discovery
CFLAGS += -I$(STM_COMMON)/Libraries/CMSIS/Include -I$(STM_COMMON)/Libraries/CMSIS/ST/STM32F4xx/Include
CFLAGS += -I$(STM_COMMON)/Libraries/STM32F4xx_StdPeriph_Driver/inc
CFLAGS += -I$(NEWLIB)/libc/include

OBJS = $(SRCS:.c=.o)


.PHONY: proj

all: proj

proj: $(PROJ_NAME).a

$(PROJ_NAME).a: $(SRCS)
	for csrc in $(SRCS) ; do \
		$(CC) -c $(CFLAGS) $$csrc ; \
	done
	$(AR) cr lib$(PROJ_NAME).a *.o
	
clean:
	rm -f *.o lib$(PROJ_NAME).a
