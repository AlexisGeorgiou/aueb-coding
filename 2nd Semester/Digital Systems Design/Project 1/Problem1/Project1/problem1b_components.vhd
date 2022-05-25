library ieee;
use ieee.std_logic_1164.all;
package problem1b_components is
	component terms3
		port(in1, in2, in3: in std_logic;
			  out1         : out std_logic);
	end component;
	
	component terms2
		port(in1, in2: in std_logic;
			  out1    : out std_logic);
	end component;
	
	component SOP3
		port(in1, in2, in3: in std_logic;
			  out1			: out std_logic);
	end component;
	
	component SOP4
		port(in1, in2, in3, in4: in std_logic;
			  out1			     : out std_logic);
	end component;
	
end package problem1b_components;

library ieee;
use ieee.std_logic_1164.all;

entity terms3 is
	port(in1, in2, in3: in std_logic;
		  out1    : out std_logic);
end terms3;
architecture model_conc of terms3 is
begin
	out1 <= in1 and in2 and in3;
end model_conc;

library ieee;
use ieee.std_logic_1164.all;

entity terms2 is
	port(in1, in2: in std_logic;
		  out1    : out std_logic);
end terms2;
architecture model_conc2 of terms2 is
begin
	out1 <= in1 and in2;
end model_conc2;

library ieee;
use ieee.std_logic_1164.all;

entity SOP3 is
	port(in1, in2, in3: in std_logic;
		  out1         : out std_logic);
end SOP3;
architecture model_conc3 of SOP3 is
begin
	out1 <= in1 or in2 or in3;
end model_conc3;

library ieee;
use ieee.std_logic_1164.all;

entity SOP4 is
	port(in1, in2, in3, in4: in std_logic;
		  out1              : out std_logic);
end SOP4;
architecture model_conc4 of SOP4 is
begin
	out1 <= in1 or in2 or in3 or in4;
end model_conc4;

	