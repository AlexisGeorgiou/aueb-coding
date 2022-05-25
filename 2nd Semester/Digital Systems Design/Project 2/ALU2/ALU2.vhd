library IEEE, ALU_1bit;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use work.ALU_1bit.all;

entity ALU2 is
	port(opcode: in std_logic_vector(2 downto 0); --operation code)
		 a, b  : in std_logic_vector(15 downto 0); --numbers for operation)
		 res   : out std_logic_vector(15 downto 0); -- operation result
		 overflow: out std_logic); --detects overflow
end ALU2;

architecture structural of ALU2 is
	signal aInvert, bInvert: std_logic;
	signal cin			   : std_logic_vector(16 downto 0);-- carry out signal is not needed due to the code's structure
	signal op			   : std_logic_vector(1 downto 0);
begin
	--Opcode decoding process (Opcode --> op)
	process(opcode)
	begin
		--AND operation
		if opcode = "000" then
		op <= "00";
		aInvert <= '0';
		bInvert <= '0';
		cin(0) <= '0';
		--OR operation
		elsif opcode = "001" then
		op <= "01";
		aInvert <= '0';
		bInvert <= '0';
		cin(0) <= '0';
		--ADD operation
		elsif opcode = "011" then
		op <= "10";
		aInvert <= '0';
		bInvert <= '0';
		cin(0) <= '0';
		--SUB operation
		elsif opcode = "010" then
		op <= "10";
		aInvert <= '0';
		bInvert <= '1';
		cin(0) <= '1';
		--NOR operation
		--NOR and AND share the same op signal, because NOR can be constructed by using an AND gate and inverting "a" and "b" signals
		elsif opcode = "101" then
		op <= "00";
		aInvert <= '1';
		bInvert <= '1';
		cin(0) <= '0';
		--XOR operation
		elsif opcode = "100" then
		op <= "11";
		aInvert <= '0';
		bInvert <= '0';
		cin(0) <= '0';
		end if;
		
		--Other values for opcode don't matter
	end process;
	
	--for statement used to carry out chosen operation between 2 16-bit numbers
	G7: for i in 0 to 15 generate
		ALUs: ALU_1bit_component port map(a(i), b(i), aInvert, bInvert, cin(i), op, cin(i+1), res(i)); --Carry out is given by this formula: cout = cin(i+1)
	end generate;
	overflow <= cin(16) xor cin(15); --0 if result overflows, else 1 if not
end structural;
	
		 
			