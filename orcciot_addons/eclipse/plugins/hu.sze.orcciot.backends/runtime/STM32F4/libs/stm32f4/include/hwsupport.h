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

#ifndef HWSUPPORT_H
#define HWSUPPORT_H

#include <stm32f4xx.h>
#include <core_cm4.h>

#define SERIAL_BUFFER_LEN 8192
struct serial_buffer {
	int write_ctr;
	int read_ctr;
	unsigned char buffer[SERIAL_BUFFER_LEN];
}; 

void conf_systick();
void conf_hw();
void powerdown();
int32_t TIM2_base();
int32_t TIM2_diff( int32_t base );
void USART2_sendbyte( unsigned char b );
void USART2_sendstring( char msg[] );
void USART2_resetrcvbuf();
int USART2_getstring( char str[],int maxlen );

#endif
