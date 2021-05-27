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


void I2C1_Configuration(void) {
	GPIO_InitTypeDef GPIO_InitStructure;
	I2C_InitTypeDef I2C_InitType;
  
/* GPIOB clock enable */
	RCC_AHB1PeriphClockCmd(RCC_AHB1Periph_GPIOB, ENABLE);

/*-------------------------- GPIO B Configuration ----------------------------*/
// PB.9 I2C1 SDA, PB.6 I2C1 SCL
	GPIO_PinAFConfig(GPIOB, GPIO_PinSource9, GPIO_AF_I2C1);
	GPIO_PinAFConfig(GPIOB, GPIO_PinSource6, GPIO_AF_I2C1);
	
	memset( &GPIO_InitStructure, 0, sizeof( GPIO_InitTypeDef ) );
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_9 | GPIO_Pin_6; 
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF;
	GPIO_InitStructure.GPIO_OType = GPIO_OType_OD;
	GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_NOPULL;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_2MHz;
	GPIO_Init(GPIOB, &GPIO_InitStructure);

	RCC_APB1PeriphClockCmd(RCC_APB1Periph_I2C1, ENABLE);
	
	I2C_InitType.I2C_ClockSpeed = 100000;
	I2C_InitType.I2C_Mode = I2C_Mode_I2C;
	I2C_InitType.I2C_OwnAddress1 = 99;
	I2C_InitType.I2C_Ack = I2C_Ack_Enable;
	I2C_InitType.I2C_AcknowledgedAddress = I2C_AcknowledgedAddress_7bit;
	I2C_InitType.I2C_DutyCycle = I2C_DutyCycle_2;
	
	I2C_Init(I2C1, &I2C_InitType);
	I2C_Cmd(I2C1, ENABLE);

}

int32_t I2C1_getregister( uint8_t devaddress, uint8_t regaddress ) {
    uint32_t status;
    
	I2C_AcknowledgeConfig( I2C1, ENABLE);
	I2C_GenerateSTART(I2C1, ENABLE);
	while( 1 ) {
			status = I2C_GetLastEvent(I2C1);
			if( ( status & I2C_EVENT_MASTER_MODE_SELECT ) == I2C_EVENT_MASTER_MODE_SELECT )
				break;
	}
	I2C_Send7bitAddress( I2C1, devaddress, I2C_Direction_Transmitter);
	while( 1 ) {
			status = I2C_GetLastEvent(I2C1);
			if( ( ( status & I2C_FLAG_AF ) & 0xFFFF ) != 0 ) {
				I2C_ClearFlag( I2C1, I2C_FLAG_AF );
				I2C_GenerateSTOP( I2C1, ENABLE);
				return -1;
			}
			if( ( status & I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED ) == I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED )
				break;
    }
	I2C_SendData( I2C1, regaddress);
	while( I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_TRANSMITTED ) != SUCCESS );
	I2C_GenerateSTOP( I2C1, DISABLE);

    I2C_GenerateSTART(I2C1, ENABLE);
	while( I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_MODE_SELECT) != SUCCESS );
	I2C_Send7bitAddress( I2C1, devaddress, I2C_Direction_Receiver);
    
	while( I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_RECEIVED ) != SUCCESS );
	I2C_AcknowledgeConfig( I2C1, DISABLE);	// As this is the last read
	uint8_t regval = I2C_ReceiveData(I2C1);
	I2C_GenerateSTOP( I2C1, ENABLE);
	return (int32_t)regval;
}

int I2C1_detect( uint8_t devaddress ) {
   uint32_t status;
    
	I2C_GenerateSTART(I2C1, ENABLE);
	while( 1 ) {
			status = I2C_GetLastEvent(I2C1);
			if( ( status & I2C_EVENT_MASTER_MODE_SELECT ) == I2C_EVENT_MASTER_MODE_SELECT )
				break;
	}
	I2C_Send7bitAddress( I2C1, devaddress, I2C_Direction_Transmitter);
	while( 1 ) {
			status = I2C_GetLastEvent(I2C1);
			if( ( ( status & I2C_FLAG_AF ) & 0xFFFF ) != 0 ) {
				I2C_ClearFlag( I2C1, I2C_FLAG_AF );
				I2C_GenerateSTOP( I2C1, ENABLE);
				return 0;
			}
			if( ( status & I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED ) == I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED )
				break;
    }
	I2C_GenerateSTOP( I2C1, ENABLE);
	return 1;
}

int I2C1_getregisters( uint8_t devaddress, uint8_t starting_reg, int num_regs, uint8_t buf[] ) {
    uint32_t status;
	uint8_t *p;
    
	I2C_AcknowledgeConfig( I2C1, ENABLE);
	I2C_GenerateSTART(I2C1, ENABLE);
	while( 1 ) {
			status = I2C_GetLastEvent(I2C1);
//            printf( "I2C1_getregisters1: %08X\xD\xA",status );
			if( ( status & I2C_EVENT_MASTER_MODE_SELECT ) == I2C_EVENT_MASTER_MODE_SELECT )
				break;
	}
	I2C_Send7bitAddress( I2C1, devaddress, I2C_Direction_Transmitter);
	while( 1 ) {
			status = I2C_GetLastEvent(I2C1);
 //           printf( "I2C1_getregisters2: %08X\xD\xA",status );
			if( ( ( status & I2C_FLAG_AF ) & 0xFFFF ) != 0 ) {
				I2C_ClearFlag( I2C1, I2C_FLAG_AF );
				I2C_GenerateSTOP( I2C1, ENABLE);
				return 0;
			}
			if( ( status & I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED ) == I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED )
				break;
    }
	I2C_SendData( I2C1, starting_reg);
	while( I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_TRANSMITTED ) != SUCCESS );
	I2C_GenerateSTOP( I2C1, DISABLE);

    I2C_GenerateSTART(I2C1, ENABLE);
	while( I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_MODE_SELECT) != SUCCESS );
	I2C_Send7bitAddress( I2C1, devaddress, I2C_Direction_Receiver);
	while( I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_RECEIVER_MODE_SELECTED ) != SUCCESS );
    
	p = buf;
	for( int i = 0 ; i < num_regs ; ++i ) {
		while( I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_RECEIVED ) != SUCCESS );
// Switch off ack at the last read
		if( i == ( num_regs - 1 ) )
			I2C_AcknowledgeConfig( I2C1, DISABLE);
		*(p++) = I2C_ReceiveData(I2C1);
	}
	I2C_GenerateSTOP( I2C1, ENABLE);
	return 1;
}

int I2C1_writeregister( uint8_t devaddress, uint8_t write_reg, uint8_t write_value ) {
	    uint32_t status;
    
	I2C_AcknowledgeConfig( I2C1, ENABLE);
	I2C_GenerateSTART(I2C1, ENABLE);
	while( 1 ) {
			status = I2C_GetLastEvent(I2C1);
			if( ( status & I2C_EVENT_MASTER_MODE_SELECT ) == I2C_EVENT_MASTER_MODE_SELECT )
				break;
	}
	I2C_Send7bitAddress( I2C1, devaddress, I2C_Direction_Transmitter);
	while( 1 ) {
			status = I2C_GetLastEvent(I2C1);
			if( ( ( status & I2C_FLAG_AF ) & 0xFFFF ) != 0 ) {
				I2C_ClearFlag( I2C1, I2C_FLAG_AF );
				I2C_GenerateSTOP( I2C1, ENABLE);
				return 0;
			}
			if( ( status & I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED ) == I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED )
				break;
    }
	I2C_SendData( I2C1, write_reg);
	while( I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_TRANSMITTED ) != SUCCESS );
    I2C_SendData(I2C1, write_value);
	
	while( I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_TRANSMITTED ) != SUCCESS );
	I2C_GenerateSTOP( I2C1, ENABLE);
	return 1;
}



