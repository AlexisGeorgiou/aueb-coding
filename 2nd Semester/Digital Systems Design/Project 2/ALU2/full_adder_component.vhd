library ieee, half_adder_component;
use ieee.std_logic_1164.all;


package full_adder_component is
	--full adder component
	component f_adder
		port(x, y, cin: in std_logic; s, cout: out std_logic);
	end component;
end package full_adder_component;


library ieee;
use ieee.std_logic_1164.all;
use work.half_adder_component.all;

entity f_adder is
	port(x, y, cin: in std_logic; s, cout: out std_logic);
end f_adder;
architecture Behavior of f_adder is
	signal s0, c0, c1: std_logic;
begin
	I0: h_adder port map(x, y, s0, c0);
	I1: h_adder port map(cin, s0, s, c1);
	cout <= c1 or c0;
end Behavior;