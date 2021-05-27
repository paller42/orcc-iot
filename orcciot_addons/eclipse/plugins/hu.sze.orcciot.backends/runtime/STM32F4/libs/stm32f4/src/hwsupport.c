/*
 * Copyright (c) 2021 Gabor Paller
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

#include <stm32f4xx.h>
#include "cycle.h"
#include <stdio.h>
#include <string.h>
#include "hwsupport.h"
#include <debug.h>

void SysTick_Handler(void) {
}

void conf_systick() {
	SysTick->LOAD  = 1640625;    /* 168MHz/512=328kHz, 10 sec */
	SysTick->VAL   = 0;     /* Load the SysTick Counter Value */
/* SysTick clock: AHB, interrupt enabled, don't run for now */
	SysTick->CTRL  =  
				SysTick_CTRL_CLKSOURCE_Msk | 
				SysTick_CTRL_TICKINT_Msk;
}

void start_systick() {
	SysTick->VAL   = 0;
	SysTick->CTRL |= SysTick_CTRL_ENABLE_Msk;
}

void stop_systick() {
	SysTick->CTRL &= ~SysTick_CTRL_ENABLE_Msk;
}


void set_clock_prescaler(uint32_t factor ) {
	uint32_t tmp;
	tmp = RCC->CFGR & ~RCC_CFGR_HPRE;
	tmp |= ( factor << 4 ) & RCC_CFGR_HPRE;
	RCC->CFGR = tmp;
	
}

void powerdown() {
	TIM_TimeBaseInitTypeDef TIM_TimeBaseInitStruct;

	set_clock_prescaler( 0xF );		// divide by 512

	TIM_TimeBaseInitStruct.TIM_Prescaler = 81;	// 2 kHz timer base frequency (2xAPB1 clock=164 kHz
	TIM_TimeBaseInitStruct.TIM_CounterMode = 0;	// Upcounter, edge-aligned
	TIM_TimeBaseInitStruct.TIM_Period = 1999;
	TIM_TimeBaseInitStruct.TIM_ClockDivision = 0;
	TIM_TimeBaseInit(TIM2, &TIM_TimeBaseInitStruct);

	__WFI();
	__WFI();

	TIM_TimeBaseInitStruct.TIM_Prescaler = 4199;	// 2 kHz timer base frequency (2xAPB1 clock=84MHz
	TIM_TimeBaseInitStruct.TIM_CounterMode = 0;	// Upcounter, edge-aligned
	TIM_TimeBaseInitStruct.TIM_Period = 1999;
	TIM_TimeBaseInitStruct.TIM_ClockDivision = 0;
	TIM_TimeBaseInit(TIM2, &TIM_TimeBaseInitStruct);
	
	set_clock_prescaler( 0x0 );	// No division	
}


void RCC_Configuration(void) {
/* --------------------------- System Clocks Configuration -----------------*/
/* GPIOA clock enable */
	RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOA, ENABLE);

/* GPIOC clock enable */
	RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOC, ENABLE);
	
/* TIM2 clock enable */
	RCC_APB1PeriphClockCmd(RCC_APB1Periph_TIM2, ENABLE);
}
  
/**************************************************************************************/
  
void GPIO_Configuration(void) {
	GPIO_InitTypeDef GPIO_InitStructure;
  
/*-------------------------- GPIO C Configuration ----------------------------*/
// PC.6 USART6_TX, PC.7 USART6_RX
	GPIO_PinAFConfig(GPIOC, GPIO_PinSource6, GPIO_AF_USART6);
	GPIO_PinAFConfig(GPIOC, GPIO_PinSource7, GPIO_AF_USART6);

	memset( &GPIO_InitStructure, 0, sizeof( GPIO_InitTypeDef ) );
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_6 | GPIO_Pin_7; 
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF;
	GPIO_InitStructure.GPIO_OType = GPIO_OType_PP;
	GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_NOPULL;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_2MHz;
	GPIO_Init(GPIOC, &GPIO_InitStructure);
  

/*-------------------------- GPIO A Configuration ----------------------------*/
// PA.2 USART2_TX, PA.3 USART2_RX
	GPIO_PinAFConfig(GPIOA, GPIO_PinSource2, GPIO_AF_USART2);
	GPIO_PinAFConfig(GPIOA, GPIO_PinSource3, GPIO_AF_USART2);

	memset( &GPIO_InitStructure, 0, sizeof( GPIO_InitTypeDef ) );
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_2 | GPIO_Pin_3; 
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF;
	GPIO_InitStructure.GPIO_OType = GPIO_OType_PP;
	GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_NOPULL;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_2MHz;
	GPIO_Init(GPIOA, &GPIO_InitStructure);
}

#define TIM2_CTR_PERIOD	10000000
volatile static uint32_t tim2_ctr;

void TIM2_Configuration(void) {
	TIM_TimeBaseInitTypeDef TIM_TimeBaseInitStruct;
	NVIC_InitTypeDef NVIC_InitStructure; 

	tim2_ctr = 0;
	TIM_TimeBaseInitStruct.TIM_Prescaler = 41999;	// 20 kHz timer base frequency (2xAPB1 clock=84MHz
	TIM_TimeBaseInitStruct.TIM_CounterMode = 0;	// Upcounter, edge-aligned
	TIM_TimeBaseInitStruct.TIM_Period = 1999;
	TIM_TimeBaseInitStruct.TIM_ClockDivision = 0;
	TIM_TimeBaseInit(TIM2, &TIM_TimeBaseInitStruct);
    
	RCC->APB1LPENR |=  RCC_APB1LPENR_TIM2LPEN;  // TIM2 active in low-power modes

	NVIC_InitStructure.NVIC_IRQChannel = TIM2_IRQn;
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 1;
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 1;
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;
	NVIC_Init(&NVIC_InitStructure);

	TIM_UpdateDisableConfig(TIM2, DISABLE);
	TIM_ITConfig(TIM2, TIM_IT_Update, ENABLE);
	TIM_Cmd(TIM2,ENABLE);
}

void TIM2_IRQHandler(void) {
	if (TIM_GetITStatus(TIM2, TIM_IT_Update) != RESET) {
		++tim2_ctr;
		tim2_ctr %= TIM2_CTR_PERIOD;
		TIM_ClearITPendingBit(TIM2, TIM_IT_Update);
	}
}

int32_t TIM2_base() {
	return (int32_t)tim2_ctr;
}

int32_t TIM2_diff( int32_t base ) {
	int32_t now = TIM2_base();
	now = now - base;
	if( now < 0 )
		now += TIM2_CTR_PERIOD;
	return now;
}

volatile static struct serial_buffer usart2_bufdesc;

void USART_Configuration(void) {
	USART_InitTypeDef USART_InitStructure;
	NVIC_InitTypeDef NVIC_InitStructure; 
  
  /* USARTx configuration ------------------------------------------------------*/
  /* USARTx configured as follow:
        - BaudRate = 115200 baud
        - Word Length = 8 Bits
        - One Stop Bit
        - No parity
        - Hardware flow control disabled (RTS and CTS signals)
        - Receive and transmit enabled
  */
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_USART6, ENABLE);
	memset( &USART_InitStructure, 0, sizeof( USART_InitTypeDef ) );
	USART_InitStructure.USART_BaudRate = 115200;
	USART_InitStructure.USART_WordLength = USART_WordLength_8b;
	USART_InitStructure.USART_StopBits = USART_StopBits_1;
	USART_InitStructure.USART_Parity = USART_Parity_No;
	USART_InitStructure.USART_HardwareFlowControl = USART_HardwareFlowControl_None;
 
	USART_InitStructure.USART_Mode = USART_Mode_Rx | USART_Mode_Tx;
 
	USART_Init(USART6, &USART_InitStructure);
	
	RCC->APB2LPENR |= RCC_APB2LPENR_USART6LPEN;	// USART6 active in low-power mode
	
	USART_Cmd(USART6, ENABLE);

	RCC_APB1PeriphClockCmd(RCC_APB1Periph_USART2, ENABLE);
	memset( &USART_InitStructure, 0, sizeof( USART_InitTypeDef ) );
	USART_InitStructure.USART_BaudRate = 115200;
	USART_InitStructure.USART_WordLength = USART_WordLength_8b;
	USART_InitStructure.USART_StopBits = USART_StopBits_1;
	USART_InitStructure.USART_Parity = USART_Parity_No;
	USART_InitStructure.USART_HardwareFlowControl = USART_HardwareFlowControl_None;
 
	USART_InitStructure.USART_Mode = USART_Mode_Rx | USART_Mode_Tx;

	USART_Init(USART2, &USART_InitStructure); 

	RCC->APB1LPENR |=  RCC_APB1LPENR_USART2LPEN;	// USART2 active in low-power mode
	
	usart2_bufdesc.write_ctr = 0;
	usart2_bufdesc.read_ctr = 0;

// Enable interrupt handling for USART2
	NVIC_InitStructure.NVIC_IRQChannel = USART2_IRQn;
	NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority = 1;
	NVIC_InitStructure.NVIC_IRQChannelSubPriority = 1;
	NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;
	NVIC_Init(&NVIC_InitStructure);
	
	USART_Cmd(USART2, ENABLE);
	USART2->CR1 |= USART_CR1_RXNEIE;
}

void USART2_sendbyte( unsigned char b ) {
		while(USART_GetFlagStatus(USART2, USART_FLAG_TXE) == RESET); // Wait for Empty
		USART_SendData(USART2, (uint16_t)b); 
}

void USART2_sendstring( char msg[] ) {
	uint16_t	c;
	char	*p = msg;
  
	while( 1 ) {
		c = (uint16_t)( *(p++) );
		if( c == 0 )
		   break;
		while(USART_GetFlagStatus(USART2, USART_FLAG_TXE) == RESET); // Wait for Empty
		USART_SendData(USART2, c); // Echo Char
	}
	printf( ">>>'%s'\n\r",msg );
}

void put_serial_buffer( volatile struct serial_buffer *buf, unsigned char c ) {
	buf->buffer[ (buf->write_ctr)++ ] = c;
	buf->write_ctr = buf->write_ctr % SERIAL_BUFFER_LEN;
}

int get_serial_buffer( volatile struct serial_buffer *buf ) {
	unsigned char c;

	if( buf->read_ctr == buf->write_ctr )
		return -1;
	c = buf->buffer[ (buf->read_ctr)++ ];
	buf->read_ctr = buf->read_ctr % SERIAL_BUFFER_LEN;
	return (int)c;
}

void USART2_IRQHandler(void) {
	unsigned char c;
	
	if( USART_GetITStatus(USART2, USART_IT_RXNE) == SET ) {
		c = (unsigned char)USART_ReceiveData( USART2 );
		put_serial_buffer( &usart2_bufdesc, c );
	}
}

void USART2_resetrcvbuf() {
	usart2_bufdesc.read_ctr = usart2_bufdesc.write_ctr;
}


int USART2_getstring( char str[],int maxlen ) {
	unsigned char c, prevc = 0;
	char	*p = str;
	int len = 0,ic = -1;
	int32_t base;
	int retval = 1;
  
	while( len < maxlen ) {
		base = TIM2_base();
		// Wait for character
		while( TIM2_diff( base ) < 10 ) {
				ic = get_serial_buffer( &usart2_bufdesc );
				if( ic >= 0 )
					break;
		}
		if( ic < 0 ) {	// timeout
			*p = 0;
			retval = 0;
			break;
		}
		c = (unsigned char)ic;
#if DEBUG
		printf( "C: %02X (%c)\r\n",c,c );
#endif
		if( c == 0x0A ) {
			if( prevc == 0x0D )
				break;
			else {
				*(p++) = c;
				++len;
				if( len >= maxlen )
					break;
			}
		} else {
			if( prevc == 0x0D ) {
				*(p++) = prevc;
				++len;
				if( len >= maxlen )
					break;
			}
			if( c != 0x0D ) {
				*(p++) = c;
				++len;
				if( len >= maxlen )
					break;
			}
			prevc = c;
		}
	}
	*p = 0;
	if( retval )
		printf("<<<'%s'\n\r",str );
	return retval;
}


void conf_hw() {
// Set up USART6 as console I/O for printf&co.
	RCC_Configuration();
	GPIO_Configuration();
	TIM2_Configuration();
	USART_Configuration();
// Configure (but don't start) SysTick
	conf_systick();
}

