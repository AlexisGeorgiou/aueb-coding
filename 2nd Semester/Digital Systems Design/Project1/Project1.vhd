library ieee, problem1b_components;
use ieee.std_logic_1164.all;
use work.problem1b_components.all;

entity Project1 is
	port(x1, x2, x3, x4, x5: in std_logic;
		  f, g: out std_logic);
end Project1;
architecture structural of Project1 is
	signal term1, term2, term3, term4, term5, term6: std_logic;

begin
	I0: terms3 port map(not x1, not x2, not x4, term1); --common term--
	I1: terms3 port map(not x5, x2, x4, term2);
	I2: terms2 port map(x5, not x2, term3);
	I3: terms3 port map(not x1, not x2, x4, term4);
	I4: terms2 port map(x2, not x3, term5);
	I5: terms3 port map(x1, x2, x3, term6);
	I6: SOP3 port map(term1, term2, term3, f);
	I7: SOP4 port map(term1, term4, term5, term6, g);
end structural;