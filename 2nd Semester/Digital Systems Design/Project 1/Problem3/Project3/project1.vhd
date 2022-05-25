entity project1 is 
	port (x1,x2,x3: in bit ;
			f: out bit);
end project1;
architecture design of project1 is
begin
	f <= (x1 or x2 or x3) and ((not x1) or (not x2));
end design;
		