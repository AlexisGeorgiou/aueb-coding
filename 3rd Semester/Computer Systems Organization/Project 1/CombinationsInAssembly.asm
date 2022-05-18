# ilias stefanis	p3180176
# alexios georgiou	p3180027
	.text
	.globl main
main:
	
	la $a0, n_prompt	# Input for n
	li $v0, 4
	syscall

	li $v0, 5
	syscall
	sw $v0, n
	
	
	la $a0, k_prompt	# Input for k
	li $v0, 4
	syscall

	li $v0, 5
	syscall
	sw $v0, k


				#if statement
	lw $t0, n
	lw $t1, k

	blt $t0, $t1, else_if		# if n < k: goto else_if
	blt $t1, $zero, else_if
	
				#inside 1st if
	lw $a0, n
	lw $a1, k
	jal combinations


	# println("C("+n+", "+k+") = "+combinations(n, k));

	la $a0, print_1_1
	li $v0, 4
	syscall

	lw $a0, n
	li $v0, 1
	syscall

	la $a0, print_1_2
	li $v0, 4
	syscall

	lw $a0, k
	li $v0, 1
	syscall

	la $a0, print_1_3
	li $v0, 4
	syscall

	lw $a0, result
	li $v0, 1
	syscall
	# end of println


	j end_if


				#inside else if
else_if:
	la $a0, warning_print
	li $v0, 4
	syscall

end_if:

	li $v0, 10
	syscall




#--------------Combinations Function--------------

combinations:
	move $t0, $a0  		# t0 = argument n
	move $t1, $a1  		# t1 = argument k
	

	# ---- !n below ----
	lw $t2, i		# t2 = iterator i
	lw $t3, comb_n		
loop_1:
	bgt $t2, $t0, end_loop_1
	mul $t3, $t3, $t2
	addi $t2, $t2, 1
	j loop_1
end_loop_1:
	sw $t3, comb_n		# stores result
	

	# ---- !k below ----
	lw $t2, i 		# resets i
	lw $t3, comb_k
loop_2:
	bgt $t2, $t1, end_loop_2
	mul $t3, $t3, $t2
	addi $t2, $t2, 1
	j loop_2
end_loop_2:
	sw $t3, comb_k	


	# ---- !(n-k) below ----
	lw $t2, i 		# resets i
	lw $t3, comb_n_k
	sub $t4, $t0, $t1	# t4 = n - k	
loop_3:
	bgt $t2, $t4, end_loop_3
	mul $t3, $t3, $t2
	addi $t2, $t2, 1
	j loop_3
end_loop_3:	
	sw $t3, comb_n_k	

	lw $t1, comb_n
	lw $t2, comb_k

	mul $t4, $t2, $t3	# t4 = comb_n_k * comb_k
	div $t5, $t1, $t4

	sw $t5, result		# stores the final result

	jr $ra
	


#-----------------------------
	.data

n_prompt:       .asciiz "Enter number of objects in the set (n): "
k_prompt:   	.asciiz "Enter number to be chosen (k): "

print_1_1:	.asciiz "C("
print_1_2:	.asciiz ", "
print_1_3:	.asciiz ") = "
warning_print:	.asciiz "Please enter n >= k >= 0"

endl:		.asciiz "\n"
allok:		.asciiz "\nIf this appears, all is ok"

n: 		.word 6
k:		.word 3

i:		.word 1
comb_n:		.word 1
comb_k:		.word 1
comb_n_k:	.word 1

result:		.word 1

