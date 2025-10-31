# HashMap initial capacity microbenchmarking-øvelse

## Oversigt

Denne øvelse udforsker hvordan HashMap initial capacity påvirker performance når man indsætter tilfældige string
key-value par. Målet er at forstå hvordan microbenchmarking virker ved at afprøve noget meget simpelt. Fokus er altså
mere på microbenchmarking end på HashMaps.

## Hvad er HashMap's initial capacity for noget?

HashMap bruger en array til at opbevare sine elementer internt. Initial capacity bestemmer størrelsen af denne array når
HashMap'et oprettes. Java's HashMap starter med en default capacity på 16. Når HashMap'et fyldes op, skal det resize
denne interne array for at have plads til flere elementer. Resizing er en dyr operation fordi alle eksisterende
elementer skal hashcodes beregnes igen og flyttes til nye positioner i den større array.

HashMaps resizer ligesom ArrayLists når de bliver for fulde, men kriteriet er anderledes. Hvor ArrayList resizer når
den er helt fyldt op, bruger HashMap et koncept kaldet load factor. Java's HashMap resizer når 75% af capacity'en er
fyldt (load factor 0.75). Dette skyldes at hash-kollisioner bliver mere sandsynlige når HashMap'et bliver mere fuldt,
hvilket forværrer performance. Ved at resize før HashMap'et er helt fuldt, holdes antallet af kollisioner nede.

Kollisioner sker når to forskellige keys hasher til samme position i den interne array. HashMap håndterer dette ved at
kæde elementerne sammen i en linked list (eller balanced tree for mange elementer). Jo flere kollisioner, jo længere
tid tager det at finde et element. Ved at sætte en passende initial capacity kan man undgå resizing helt og holde
kollisionssandsynligheden lav, hvilket giver bedre performance for både insertion og lookup operationer.

## Hvad vi tester

- HashMap resize overhead: Hvor stor performance-straf betaler vi når HashMap skal resize?
- Parameter-udforskning: Hvilke faktorer påvirker optimiseringens indvirkning?
- Microbenchmarking-metodik: Hvordan designer man meningsfulde performance-tests

## Benchmark-parametre

### Initial capacity strategier

- DEFAULT: `new HashMap<>()` (starter med capacity 16)
- EXACT: `new HashMap<>(insertionCount)` (perfekt initial størrelse)
- UNDERSIZED: `new HashMap<>(insertionCount / 4)` (for lille, vil resize)
- OVERSIZED: `new HashMap<>(insertionCount * 2)` (for stor, spilder hukommelse)

### Variable parametre

- Insertion count: 1.000, 10.000 entries (fokus på størrelser hvor optimisering har betydning)
- Key length: 32 tegn (realistisk størrelse)
- Value length: Fast på 16 tegn

### Benchmark-metoder

- benchmarkInsertion: Ren insertion performance
- benchmarkInsertionAndLookup: Indsæt alle, så lookup alle

## Forventede resultater

### Medium data-sæt (1.000 entries)
- Lille men målbar forskel mellem capacity-strategier
- HashMap resizing overhead begynder at være synlig

### Store data-sæt (10.000 entries)

- Klar performance-forskel mellem strategier
- EXACT capacity bør være hurtigst
- UNDERSIZED bør være langsomst (flere resizes)
- DEFAULT et sted imellem

### Vigtige erkendelser

- Hvornår optimisering har betydning: Performance-forskelle bliver signifikante med større data-sæt
- Real-world relevans: Hvis du kender den forventede størrelse, er initial capacity en simpel optimisering
- Måle-metode: JMH håndterer JVM warmup, GC-effekter og statistisk analyse

## Extension muligheder

### HashMap vs TreeMap sammenligning
Projektet indeholder også `MapComparisonBenchmark.java` som er et skelet til at sammenligne HashMap og TreeMap performance:

- TODO: Implementer benchmark-metoder for insertion, lookup og sorted iteration
- TODO: Sammenlign O(1) HashMap performance med O(log n) TreeMap performance
- TODO: Demonstrer TreeMaps fordel ved sorted key iteration
- TODO: Diskuter trade-offs mellem performance og funktionalitet

### Out of scope

1. Memory usage: Vi benchmarker ikke hukommelsesforbrug med `-prof gc` for at se allocation-mønstre
2. String collision analyse: Vi prøver ikke bevidst at skabe flere kollisioner med keys der hasher til det samme
3. Concurrent access: Vi prøver ikke smartere implementeringer af HashMaps såsom ConcurrentHashMap
