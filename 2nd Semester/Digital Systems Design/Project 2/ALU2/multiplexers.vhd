library ieee;
use ieee.std_logic_1164.all;
package multiplexers is
    -- 2-1 multiplexer component
    component mux2to1
        port(w0, w1, s: in std_logic; out1: out std_logic);
    end component;
    
	-- 4-1 multiplexer component
	component mux4to1
		port(w0, w1, w2, w3: in std_logic;
			s			   : in std_logic_vector(1 downto 0);
			out1			   : out std_logic);
		end component;
end package multiplexers;

library ieee;
use ieee.std_logic_1164.all;

entity mux2to1 is
	port(w0, w1: in std_logic;
		  s	  : in std_logic_vector(0 downto 0);
		  out1: out std_logic);
end mux2to1;
architecture Behavior of mux2to1 is
begin 
	with s select
		out1 <= w0 when "0",
				w1 when others;
end Behavior;
			

library ieee;
use ieee.std_logic_1164.all;

entity mux4to1 is
	port(w0, w1, w2, w3: in std_logic;
		 s			   : in std_logic_vector(1 downto 0);
		 out1		   : out std_logic);
end mux4to1;
architecture Behavior of mux4to1 is
begin
	with s select
		out1 <= w0 when "00",
				w1 when "01",
				w2 when "10",
				w3 when others;
end Behavior;