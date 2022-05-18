.data

ask_input1:  
			.asciiz "Enter number of objects in the set (n): "

ask_input2:
			.asciiz "Enter number to be chosen (k): "
			
print1:
			.asciiz "C(n,k)="

print2: 
			.asciiz "Please enter n>=k>=0"

.text
.globl main

main:


li $v0,4						#print string
la $a0,ask_input1
syscall
			
			
li $v0,5						#read int
syscall
add $s0,$v0,$zero				#storing n in $s0


li $v0,4						#print string
la $a0,ask_input2
syscall

li $v0,5
syscall
move $s1,$v0					#storing k in $s1
sub $s2,$s0,$s1					#storing n-k in $s2


bgt $s1,$s0,else1				#if (k>n)pigaine sto else
ble $s1,$zero,else1				#if (k<0)pigaine sto else

li $v0,4						#print string
la $a0,print1
syscall
jal combinations
move $t6,$v0					#store result in $t6
li $v0,1
li $t6,5						#print int
syscall


else1:
	li $v0,4					#print string
	la $a0,print2		


la $v0,10                       #terminate program
syscall



combinations:

li $t0,1
sw $t0,i				#i=1

li $t1,1
sw $t1,factorial_n     #factorial_n=1

again:	
	
	bgt $t0,$s0, exit1		#if (i>n) phgaine sto exit1
	lw $t1,factorial_n
	mul $t1,$t0,$t1			
	sw $t1,factorial_n		#factorial_n*=i
	addi $t0,$t0,1          
	sw $t0,i				#i+=1
	j again

exit1: 

li $t2,1
sw $t2,factorial_k			#factorial_k=1

again2:

	bgt $t0,$s1,exit2	#if (i>k) phgaine sto exit2
	lw $t2,factorial_k
	mul $t2,$t0,$t2			
	sw $t2,factorial_k		#factorial_k*=i
	addi $t0,$t0,1          
	sw $t0,i				#i+=1
	j again2
	
exit2:

li $t3,1
sw $t3,factorial_n_k		#factorial_n_k=1


again3:

	bgt $t0,$s2, exit		#if (i>n-k) phgaine sto exit1
	lw $t3,factorial_n_k
	mul $t3,$t0,$t3			
	sw $t3,factorial_n		#factorial_n_k*=i
	addi $t0,$t0,1          
	sw $t0,i				#i+=1
	j again3

exit:
	lw $t4,denominator
	mul $t4,$t2,$t3
	sw $t4,denominator
	
	lw $t5,a
	div $t5,$t1,$t4 
	sw $t5,a				#a=factorial_n/denominator;
			
	add $v0,$t5,$zero
	jr $ra


	


	
	
