--Used to make full adder.

library ieee;
use ieee.std_logic_1164.all;	
package half_adder_component is
	-- half adder component
		component h_adder
			port(x, y: in std_logic; s, c: out std_logic);
		end component;
end package half_adder_component;

library ieee;
use ieee.std_logic_1164.all;

entity h_adder is
	port(x, y: in std_logic; s, c: out std_logic);
end h_adder;
architecture Behavior of h_adder is
begin
	s <= x xor y;
	c <= x and y;
end Behavior;