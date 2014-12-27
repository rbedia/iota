grammar Board;

board:
    (row NL)*
    row
    NL?
    EOF;

row:
    SS?
    (cell SS)*
    cell;

cell:
    Card;

Card: Color Shape Count;

Color: 'R' | 'G' | 'B' | 'Y' | '.';
Shape: 'O' | 'S' | 'T' | '+' | '.';
Count: '1' | '2' | '3' | '4' | '.';

SS: ' ';
NL:  '\r'? '\n';
