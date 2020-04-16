Update-uri - etapa 2

update - Sergiu - 16.04 - rocada + schimbarea functiei de evaluare
update - Sergiu - 15.04 - alfa-beta

update - Sergiu - 13.04
- adaugare conditie de terminare joc
- evaluarea se calculeaza si in functie de pozitia pionilor

update - Sergiu - 12.04
- fixat bug minimax
- am adaugat clasa PieceSquareValues ca sa implementam cateva euristici legate
de pozitia pieselor pe tabla

update - Sergiu - 08.04
- mutarea metodelor folosite pentru interpretarea mutarilor adversarului in
clasa OpponentMove
- modularizare clasa Game
- modificare metode din clasa Game + noua metoda MakeMove stie sa faca mutari
speciale ale pionului

update - Sergiu - 05.04
- modificare updatePossibleMoves pentru Rege - nu mai verifica daca e in sah
	cand calculeaza mutarile legale, verificarea se va face in alta functie
- modificare functia makeMove - face "undo" la mutare daca in urma mutarii
	regele ajunge in sah
- modificarea functiilor de updatePossibleMoves ca sa adauge in lista de mutari
	posibile un string care include si sursa si destinatia (de forma e2e4,
	inainte includea doar destinatia)
- introducerea unei liste allPossibleMoves in clasa Game care sa retina toate
	mutarile legale ale engine-ului dintr-o stare
- modificari makeMove - modularizare + ia mutarile din allPossibleMoves acum
- bugfix in main (se bloca cateodata cand schimbam partea pe care joaca bot-ul)

update - Andreea, Sergiu, Alin - 04.04
- adaugare functii eval, updateAllPossibleMoves, minimax


Proiect PA 2020 - Etapa 1

Nume Echipa: Regina si Nebunii

Membri:
Dutulescu Andreea - 325CB (capitan)
Toader Sergiu - 325CB
Ciobanu Alin - 325CB

--------------------------------------------------------------------------------

Instructiuni de compilare:

-se executa 'make build' in terminal pentru obtinerea fisierelor .class
-se executa 'xboard' pentru a deschide fereastra platformei xboard
-se selecteaza Engine -> Load first engine
-se alege directorul curent (.) ca Engine Directory si 'make run' ca Engine
command
-dupa testare se executa 'make clean' pentru stergerea fisierelor .class 

--------------------------------------------------------------------------------

Structura proiectului

Clasa Engine - contine metoda main, realizeaza interactiunea cu platforma
xboard, primeste si interpreteaza comenzile venite de la xboard si trimite
comenzi inapoi.

Clasa Game - realizeaza reprezentarea interna a tablei de joc prin matricea de
piese grid, actualizand permanent aceasta matrice dupa mutarea fiecarei piese.
Contine metode pentru mutarea oponentului, mutarea engine-ului precum si multe
metode auxiliare (pentru rocada, en passant, verificare sah getteri pentru
indici).

Clasa Piece - clasa abstracta care retine atributele unei piese (pozitie, mutari
legale, culoare) si care contine metoda abstracta updatePossibleMoves care
determina mutarile legale posibile ale unei piesa la un moment dat si care este
implementata in clasele copii;

Clasele, Pawn, Rook, Bishop, Knight, Rook, Queen, King - clase pentru fiecare
piesa in parte, extind clasa Piece si implementeaza metoda updatePossibleMoves,
facand-o sa actualizeze ArrayList-ul possibleMoves cu mutarile legale ale
piesei respective.

Clasa Empty - o subclasa speciala a clasei piece - nu are mutari legale,
marcheaza faptul ca pe pozitia sa nu se afla nicio piesa

--------------------------------------------------------------------------------

Detalii despre abordarea algoritmica a etapei:

Pentru etapa curenta, nu exista algoritmi minimax/alpha-beta/euristici
implementate. Engine-ul ia prima piesa de pe tabla care are mutari legale si
executa prima mutare legala a sa. Daca nu mai exista mutari legale, da resign.
De asemenea, engine-ul da resign daca este pus in sah, sau daca urmatoarea piesa
care are mutari legale ar expune regele la sah daca si-ar executa mutarea.

Engine-ul interpreteaza si actualizeaza corect grid-ul pentru toate mutarile
adversarului (toate piesele din joc + cazuri speciale: en passant, rocada,
promovare pion in orice piesa).

Engine-ul executa mutari legale pentru pion (2 pozitii fata, 1 pozitie fata,
cuceriri, en passant, promovare in regina), cal, nebun, tura, regina.
Nu poate muta regele sau realiza rocada.

Poate juca atat pe alb, cat si pe negru, executand mutari similare.


--------------------------------------------------------------------------------

Surse de inspiratie:

Pentru realizarea proiectului pana in prezent, ne-am folosit de informatiile de
pe forum. De asemenea, pentru partea de interfatare cu programul XBoard, ne-am
inspirat de la engine-ul TSCP (http://tckerrigan.com/Chess/TSCP/).

--------------------------------------------------------------------------------

Update-uri / responsabilitati:

Etapa 1:

update - Sergiu - 15.03
-- engine-ul va da resign daca urmatoarea mutare legala va determina expunerea
regelui la sah

update - Sergiu + Andreea - 13.03
-- engine-ul e capabil sa execute miscari de tip en passant atunci cand este
posibil
-- engine-ul recunoaste mutarile de tip rocada ale oponentului si actualizeaza
grid-ul
-- engine-ul recunoaste transformarile pionului in alta piesa ale oponentului
si actualizeaza grid-ul
-- engine-ul transforma mereu in regina pionul atunci cand ajunge cu el in baza
oponentului
-- implementare mutari posibile pentru tura, nebun, regina, cal
-- engine-ul recunoaste cand regele lui este atacat de piesele mentionate mai
sus si da resign 

update - Sergiu - 12.03
-- bug fix - engine-ul jucand pe alb nu recunostea sahul si mutarile
en passant ale oponentului

update - Andreea + Sergiu + Alin - 12.03
-- rezolvat bug - engine-ul nu stia sa inceapa cand era alb in modul two
machines

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
