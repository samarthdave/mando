//Add4.tst
load Add4.hdl,
output-file Add4.out,
compare-to Add4.cmp,
output-list a%B1.4.1 b%B1.4.1 out%B1.4.1 carry%B3.1.3;

set a %B0000,
set b %B0000,
eval,
output;

set a %B0011,
set b %B1100,
eval,
output;

set a %B1100,
set b %B0011,
eval,
output;

set a %B1110,
set b %B0011,
eval,
output;

set a %B1111,
set b %B1101,
eval,
output;

set a %B0101,
set b %B0011,
eval,
output;