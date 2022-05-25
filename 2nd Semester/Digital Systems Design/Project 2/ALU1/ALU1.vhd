library IEEE, full_adder_component, multiplexers;
use ieee.std_logic_1164.all;
use work.full_adder_component.all;
use work.multiplexers.all;

entity ALU1 is
	port(a, b, aInvert, bInvert, cin: in std_logic; 
		  op								  : in std_logic_vector(1 downto 0);
		  cout, res						  : out std_logic);
end ALU1;
architecture structural of ALU1 is
	signal na, nb, add_op, or_op, and_op, xor_op: std_logic;
begin
	I0: mux2to1 port map(a, not a, aInvert, na); -- Inverter for a
	I1: mux2to1 port map(b, not b, bInvert, nb); -- Inverter for b
	I2: f_adder port map(na, nb, cin, add_op, cout); --Full adder implementation
	
	--Operation Declarations
	and_op <= na and nb; --AND operation
	or_op <= na or nb; --OR operation
	xor_op <= na xor nb; --XOR operation
	
	I3: mux4to1 port map(and_op, or_op, add_op, xor_op, op, res); --Operation choice using a 4-1 multiplexer
end structural;
	