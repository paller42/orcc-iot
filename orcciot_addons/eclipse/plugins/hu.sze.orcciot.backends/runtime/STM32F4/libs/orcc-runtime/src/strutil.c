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

#include <string.h>
#include <stdio.h>
#include "types.h"

// Number of temporary strings that can be allocated at the same time. This determines the number of nested string operations
// increase this number if you run into problems with nesting
#define STRHEAP_SIZE    20
string strheap[STRHEAP_SIZE];
int strheap_ptr = 0;

char *alloc_str() {
    char *str = strheap[strheap_ptr];
    strheap_ptr = ( strheap_ptr + 1) % STRHEAP_SIZE;
    return str;
}

char *concat_str( char *str1, char *str2 ) {
    char *dest = alloc_str();
    strcpy( dest,str1 );
    strcat( dest,str2 );
    return dest;
}

char *string_operand_string( char *oper ) {
    char *dest = alloc_str();
    strcpy( dest,oper );
    return dest;
}

char *string_operand_int( int oper ) {
    char *dest = alloc_str();
    sprintf(dest,"%d",oper );
    return dest;
}

char *string_operand_float( float oper ) {
    char *dest = alloc_str();
    sprintf(dest,"%f",oper );
    return dest;
}

char *string_operand_uint( unsigned int oper ) {
    char *dest = alloc_str();
    sprintf(dest,"%u",oper );
    return dest;
}
