
--- README cu update-uri ---

update - Sergiu + Andreea -- 13.03
-- engine-ul recunoaste mutarile de tip rocada ale oponentului si actualizeaza
grid-ul
-- engine-ul recunoaste transformarile pionului in alta piesa ale oponentului
si actualizeaza grid-ul
-- engine-ul transforma mereu in regina pionul atunci cand ajunge cu el in baza
oponentului
-- implementare mutari posibile pentru tura, nebun, regina
-- engine-ul recunoaste cand regele lui este atacat de piesele mentionate mai
sus si da resign 

update - Sergiu - 12.03
-- bug fix - engine-ul jucand pe alb nu recunostea sahul si mutarile
en passant ale oponentului

update - Andreea + Sergiu + Alin - 12.03
-- rezolvat bug - engine-ul nu stia sa inceapa cand era alb in modul two machines

update - Sergiu - 11.03
-- engine-ul recunoaste mutarile de tip en passant ale oponentului
(testat doar cu engine ul pe negru)

update - Sergiu + Andreea + Alin - 10.03
-- rezolvat bug-uri
-- implementat sah-mat (resign cand e in sah)
-- adaugat ArrayList in loc de vector

update - Sergiu - 10.03

-- mutarile oponentului actualizeaza grid-ul
-- pionii prioritizeaza cucerirea pieselor (am schimbat iar ordinea if-urilor)

update - Sergiu - 09.03

-- coding style (am mai redus din liniile kilometrice)
-- se executa mai intai mutarea de 2 pozitii la pion daca este posibil (am
schimbat ordinea if-urilor)
-- makefile
-- implementare restul de comenzi (black, white, force, go) - white/black
modifica momentan doar o variabila "side" din clasa Game
