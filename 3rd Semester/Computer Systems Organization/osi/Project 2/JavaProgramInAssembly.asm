# цеыяциоу акениос - кафаяос (P3180027)
# гкиас стежамгс (P3180176)


.text
.globl main

main:
	lw $s0, N # loads N from data (integer)
	lw $s1, keys # keys = 0
	li $s2, 0 # key = 0
	li $s3, 0 # pos = 0
	li $s4, 0 # choice = 0
	li $s5, 0 # telos = 0
	la $s6, hash # pointer of hash
	li $s7, 0 # Hash[i]
	j loop
	
loop:
	bnez $s5, final_exit #if telos != 0, go to final_exit implements java code of "while (telos == 0);"
	la $a0, Menu_messages
	li $v0, 4
	syscall #Prints menu messages
	li $v0, 5
	syscall #Reads choice as an integer and loads it in $v0
	#Branching depending on choice
	beq $v0, 1, choice1insertkey
	beq $v0, 2, choice2findkey
	beq $v0, 3, choice3displaytable
	beq $v0, 4, choice4telos
	j loop # If another value is inserted the program will reask for a choice number
	#Branching depending on choice
	
	
choice1insertkey:
	la $a0, givemekeymsg
	li $v0, 4
	syscall #prints givemekeymsg
	li $v0, 5
	syscall # reads key and loads it into $v0
	bgtz $v0, insertkeyfunctionbranch #if key > 0 go to the branch that starts the function
	la $a0, keygreaterthanzeromsg # if we are here key <= 0 
	li $v0, 4
	syscall # prints key greater than zero message"
	j loop
	
choice2findkey:
	la $a0, givekeymsg
	li $v0, 4
	syscall # prints "Give key to search for:"
	li $v0, 5
	syscall # Reads the key and loads it in $v0
	la $a0, hash
	move $a1, $v0 #Moves the key to $a1
	jal findkeyfunction # $a0 = hash and $a1 = key
	#function will return at $v0 the position (integer)
	beq $v0, -1, choice2printkeynotinhashtable
	move $s3, $v0 # $s3 = pos
	
	la $a0, keyvalue
	li $v0, 4
	syscall #prints "Key value = "
	
	la $s6, hash # $s6 = the address of the start of hash
	mul $s3, $s3, 4 # pos = pos * 4
	add $s6, $s6, $s3 # $s6 = hash[pos*4]
	lw $a0, ($s6) # loads in $a0 the value of the word in $s6 address (the hash[pos])
	li $v0, 1
	syscall #prints hash[pos]
	
	la $a0, tableposition
	li $v0, 4
	syscall #prints "Table position = "
	
	move $a0, $s3
	div $a0, $a0, 4 #Position was multiplied by 4 so it is used as a step, now we divide again
	li $v0, 1
	syscall #prints pos (integer)
	
	j loop
	

choice3displaytable:
	la $a0, hash
	jal displaytablefunction # $a0 has hash
	j loop
	
choice4telos:
	li $s5, 1
	j loop

insertkeyfunctionbranch:
	la $a0, hash
	move $a1, $v0 #Moves the key to $a1
	jal instertkeyfunction # $a0 = hash and $a1 has the key
	j loop

choice2printkeynotinhashtable:
	la $a0, keynotinhashtable
	li $v0, 4
	syscall #Prints message
	j loop

final_exit:
	li $v0, 10
	syscall
		

		
#--------------------------FUNCTIONS-------------------------#

#--------insertkeyfunction----------#
# INPUT: $a0 = hash, $a1 = key
# OUTPUT: None
# Adds a key into the hash table

instertkeyfunction:
	li $t0, 0 # $t0 = position
	move $t1, $a0 # $t1 = hash address starting point
	move $t2, $a1 # $t2 = k (= key )
	lw $t3, N # $t3 = N
	# $s1 = keys = 0
	li $t5, 0 # helper
	
	addi $sp, $sp, -4 #SAVES RETURN ADDRESS AFTER INSERT FUNCTION
	sw $ra, ($sp)
	 
	# We already have on $a0 the hash and on $a1 the key
	jal findkeyfunction
	# Now we have the position (integer) on register $v0
	lw $ra, ($sp) #LOADS RETURN ADDRESS AFTER INSERT FUNCTION AFTER WE RUN THE 2 FUNCTIONS
	
	#Initializing temp registers after function run
	move $t1, $a0 # $t1 = hash address starting point
	move $t2, $a1 # $t2 = k (= key )
	lw $t3, N # $t3 = N
	# $s1 = keys = 0
	li $t5, 0 # helper
	#Initializing temp registers after function run
	
	
	move $t0, $v0 #position = findkey(hash, k)
	
	bne $t0, -1, insertkeykeyisalreadyinhashtable # if position != -1 go print msg and exit
	bge $s1, $t3, insertkeyhashtableisfull # if keys >= N go print msg and exit
	
	# We already have on $a0 the hash and on $a1 the key
	jal hashfunctionfunction
	
	
	#Initializing temp registers after function run
	move $t1, $a0 # $t1 = hash address starting point
	move $t2, $a1 # $t2 = k (= key )
	lw $t3, N # $t3 = N
	# $s1 = keys = 0
	li $t5, 0 # helper
	#Initializing temp registers after function run
	
	
	lw $ra, ($sp) #LOADS RETURN ADDRESS AFTER INSERT FUNCTION AFTER WE RUN THE 2 FUNCTIONS
	addi $sp, $sp, 4
	
	# Now we have the position (integer) on register $v0
	move $t0, $v0 #position = hashfunction(hash, k)
	
	# hash[position] = k
	mul $t5, $t0, 4 # $t5 = 4 * pos
	add $t5, $t5, $t1 # $t5 = 4* pos + starting point address
	sw $t2, ($t5) # Saves the key stored in ($t2) in the address of hash[position] ($t5)
	# hash[position] = k
	
	addi $s1, $s1, 1 # keys++
	jr $ra
	

insertkeykeyisalreadyinhashtable:
	la $a0, keyisalreadyinhashtablemsg
	li $v0, 4
	syscall #Prints message
	jr $ra

insertkeyhashtableisfull:
	la $a0, hashtableisfullmsg
	li $v0, 4
	syscall #Prints message
	jr $ra




#--------hashfunctionfunction----------#
# INPUT: $a0 = hash, $a1 = key
# OUTPUT: $v0 = position
# Calculates the position

hashfunctionfunction:
	li $t0, 0 # position = $t0 = 0 (int position;)
	lw $t1, N # $t1 = N
	move $t2, $a0 # t2 = hash table start address pointer
	li $t3, 0 # helper
	li $t5, 0 # helper 
	
	divu $a1, $t1 #stores the modulo of k % N at $hi register
	mfhi $t0 # moves to position $t0 register the $hi value (position = k % N) 
	
	mul $t5, $t0, 4 # t5 = pos * 4
	add $t3, $t2, $t5 # $t3 = starting point + 4 *  position 
	lw $t4, ($t3) # loads into $t4 the has[pos] value
	# We loaded the first hash[pos] which is always equal to on first time hash[k%N]
	
hashfunctionloop:
 	beqz $t4, exithashfunction # if hash[pos] == 0 go to exit
	addi $t0, $t0, 1 # position++
	
	divu $t0, $t1 # stores the modulo of position % N at $hi register
	mfhi $t0 # moves the modulo to position register "position %= N"
	
	move $t2, $a0 # $t2 gets reset to the starting address of the hash table
	
	#load next hash[pos] for next loop
	mul $t5, $t0, 4 # $t5 = 4*position it's a Register that helps keep track on hash[position] value
	add $t3, $t2, $t5 # $t3 = starting point + 4 * pos
	
	lw $t4, ($t3) # we load the next word on $t4 for future loop
	j hashfunctionloop
	
exithashfunction:
	move $v0, $t0 # t0 = position is moved to v0 so it can get out of the function
	jr $ra



#--------findkey function----------#
# INPUT: $a0 = hash, $a1 = key
# OUTPUT: $v0 = position
# Searches for the key in hash table, if found it returns the position else -1.

findkeyfunction:
	li $t0, 0 # $t0 = position
	li $t1, 0 # $t1 = i
	li $t2, 0 # $t2 = found
	move $t3, $a1 # $t3 = k (= key)
	lw $t4, N # $t4 = N
	move $t5, $a0 # $t5 = hash
	li $t6, 0 # $t6 = hash[pos] in this register each item value of the hash table will be loaded
	li $t7, 0 # helping with multiplications on hash[pos]
	
	divu $t3, $t4 # stores the modulo of k % N at $hi register
	mfhi $t0 # moves the modulo to position register (position = k % N)
	
findkeyfunctionloop:
	bge $t1, $t4, findkeyexitloop # If i >= N exit while loop
	bnez $t2, findkeyexitloop # If found != 0
	addi $t1, $t1, 1 # i++
	
	mul $t7, $t0, 4 # $t7 = 4*pos
	add $t5, $t5, $t7 # $t5 = starting address + 4 * pos
	lw $t6, ($t5) # $t6 = hash[pos]
	la $t5, hash # resets the $t5 to starting address point of hash table
	beq $t6, $t3, findkeyfoundsettoone
	
	addi $t0, $t0, 1 # position++
	
	divu $t0, $t4 # stores the modulo of position % N at $hi register
	mfhi $t0 # moves the modulo to position register "position %= N"
	
	j findkeyfunctionloop
	
	

findkeyfoundsettoone:
	li $t2, 1 # found = 1
	j findkeyfunctionloop
	
findkeyexitloop:
	beq $t2, 1, findkeyreturnpos # If found == 1
	li $v0, -1 
	jr $ra #returns -1

findkeyreturnpos:
	move $v0, $t0 
	jr $ra #returns position (integer)
	


#--------displaytablefunction----------#
# INPUT: $a0 = hash
# OUTPUT: None
# Prints the hash table
displaytablefunction:
	move $t2, $a0 #t2 = hash
	
	la $a0, poskeydisplaymsg
	li $v0, 4
	syscall #prints display function message
	
	li $t0, 0 # t0 = i = 0
	lw $t1, N #t1 = N
	
fordisplayloop:
	bge $t0, $t1, exitdisplayfunction # if $t0 >= $t1 goto exit  (i >= N exiting for loop)
	
	la $a0, spacemsg
	li $v0, 4
	syscall #prints space
	
	move $a0, $t0
	li $v0, 1
	syscall #prints i
	
	la $a0, spacemsg
	li $v0, 4
	syscall #prints space
	
	lw $a0, ($t2) #loads into $a0, the word that $t2 address showing
	li $v0, 1
	syscall #prints hash[i]
	
	addi $t0, $t0, 1 #i++
	addi $t2, $t2, 4 # adds 4 to the pointer of hash table to show next word
	
	la $a0, newline
	li $v0, 4
	syscall
	
	j fordisplayloop
	
exitdisplayfunction:
	jr $ra
	



###############################################################
###############################################################	
		
.data

N: .word 10 # We read it in $t since it doesn't change in code even if it's global
# N can be set to any number as long as there is space in hash, for example for space 4000 you can have
# a hash table with 1000 positions. Max_N = space // 4
#hash: 1, 2, 44, 20, 1, 20, 10, 9, 2, 0  # Table used for testing
#hash: .word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 

hash: .space 4000 #max N is 1000 ### for (int i=0; i<N; i++) hash[i] = 0;
	.align 2 #it probably needs an align
keys: .word 0
newline: .asciiz "\n"
Menu_messages: .asciiz " \nMenu\n1.Insert Key\n2.Find Key\n3.Display Hash Table\n4.Exit\nChoice?\n"
givemekeymsg: .asciiz "\nGive new key (greater than zero): "
keygreaterthanzeromsg: .asciiz "\nkey must be greater than zero"
givekeymsg: .asciiz "\nGive key to search for: "
poskeydisplaymsg: .asciiz "\npos key\n"
spacemsg: .asciiz " "
keynotinhashtable: .asciiz "\nKey not in hash table."
keyvalue: .asciiz "\nKey value = "
tableposition: .asciiz "\nTable position = "
keyisalreadyinhashtablemsg: .asciiz "\nKey is already in hash table."
hashtableisfullmsg: .asciiz "\nhash table is full"
