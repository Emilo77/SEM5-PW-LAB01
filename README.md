# Laboratorium - tworzenie wątków

Tematem zajęć są wątki w Javie.

Do scenariusza dołączone są programy przykładowe:

- [SingleThread.java ](https://github.com/Emilo77/SEM5-PW-LAB1/blob/master/SingleThread.java)

- [TwoThreads.java](https://github.com/Emilo77/SEM5-PW-LAB1/blob/master/TwoThreads.java)

- [TwoWriters.java]( https://github.com/Emilo77/SEM5-PW-LAB1/blob/master/TwoWriters.java)

- [TwoWritersSynchronized.java ](https://github.com/Emilo77/SEM5-PW-LAB1/blob/master/TwoWritersSynchronized.java)

### Współbieżność i równoległość

Współbieżność (ang. concurrency) i równoległość (ang. parallelism) to dwa powiązane, ale jednak nie tożsame pojęcia.

*Współbieżność* oznacza zdolność do wykonywania obliczeń niezależnie od siebie, bez wpływu na ich ostateczny wynik. Jej przeciwieństwem jest sekwencyjność. Obliczenia sekwencyjne muszą być wykonane w ustalonej kolejności.

*Równoległość* natomiast oznacza zdolność do wykonywania wielu obliczeń w tym samym czasie. Możliwa jest współbieżność bez równoległości.

### Procesy i wątki

Proces to wykonujący się program wraz z dynamicznie przydzielanymi mu przez system operacyjny zasobami, np.: procesorem, pamięcią operacyjną, plikami, urządzeniami wejścia-wyjścia, oraz ze środowiskiem umożliwiającym wykonanie. Wielokrotne wykonanie tego samego programu oznacza tworzenie, za każdym razem, odrębnego procesu.

Współczesne wielozadaniowe systemy operacyjne udostępniają mechanizm *przełączania kontekstu* (ang. context switching), umożliwiając w ten sposób zachowanie bieżącego stanu procesu (czyli stanu konkretnego wykonania określonego programu), a następnie wznowienie (zwykle innego) procesu (czyli przywrócenie stanu wykonywania - zazwyczaj innego - programu). Systemy wielozadaniowe pozwalają więc na współbieżne wykonywanie wielu procesów, z których każdy ma swój kontekst i swoje zasoby.

*Wątek* (ang. thread) to sekwencja działań, która może wykonywać się równolegle z innymi sekwencjami działań (wątkami) w kontekście tego samego procesu.

Każdy proces ma co najmniej jeden wykonujący się wątek, natomiast w systemach wielowątkowych proces może wykonywać współbieżnie wiele wątków. Wszystkie one wykonują się w tej samej przestrzeni adresowej procesu.

W systemach jednoprocesorowych współbieżność wątków realizowana jest przez mechanizm przydzielania czasu procesora poszczególnym wątkom. O dostępie wątków do procesora decyduje systemowy zarządca wątków: każdy wątek uzyskuje dostęp do procesora na krótki czas (kwant czasu), po upływie którego procesor przekazywany jest innemu wątkowi.

Podstawowa różnica pomiędzy procesami i wątkami polega na tym, że różne wątki jednego procesu mają dostęp do zasobów (w tym przestrzeni adresowej) i kontekstu tego procesu. Oznacza to, że przekazywanie procesora innemu wątkowi w obrębie tego samego procesu może być wykonane szybciej niż przełączenie kontekstu pomiędzy różnymi procesami. Utworzenie nowego procesu jest bardziej kosztowne niż utworzenie nowego wątku.

Z punktu widzenia programisty dostęp wątków jednego procesu do zasobów i kontekstu tego procesu ma zarówno zalety jak i wady. Zaletą jest możliwość łatwego dostępu do wspólnych danych programu. Wadą - brak ochrony danych programu przed zmianami dokonywanymi współbieżnie przez różne wątki, co może prowadzić do niespójności tych danych. Aby tego uniknąć konieczne jest stosowanie specjalnych mechanizmów synchronizacji w dostępie wątków do tych danych.

### Wątki w Javie

W Javie wątek jest reprezentowany przez obiekt klasy [Thread](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Thread.html).

Statyczna metoda `currentThread()` klasy `Thread` zwraca aktualnie wykonywany wątek.

Podczas swego życia w systemie, wątek może być w różnych stanach. Po utworzeniu, ale przed uruchomieniem, wątek jest w stanie `NEW`. Działający wątek jest w stanie `RUNNABLE`. Po zakończeniu pracy wątek jest w stanie `TERMINATED`. Poza tym, wątek może być w jednym z kilku stanów oczekiwania na wystąpienie jakiegoś zdarzenia.

Metoda `isAlive()` sprawdza, czy wątek nie zakończył działania.

Inne, wybrane cechy wątków:

- Wątki mają nazwy. Nazwę wątku można odczytać metodą `getName()`. Nazwę, domyślnie nadaną przez maszynę wirtualną, można zmienić metodą `setName()`. Można też określić nazwę wątku, tworząc go.
  Nadanie wątkom znaczących nazw pomaga w debugowaniu programu.

- Szeregowanie wątków może uwzględniać ich priorytety. Priorytet wątku jest liczbą całkowitą od `MIN_PRIORITY`, czyli 1, do `MAX_PRIORITY`, czyli 10. Domyślną wartością jest `NORM_PRIORITY`, czyli 5. Priorytet wątku można odczytać metodą `getPriority()`.

  Specyfikacja maszyny wirtualnej Javy nie daje żadnych gwarancji co do wpływu priorytetu     wątków na wykonanie programu.

  Programując współbieżnie w Javie nie będziemy korzystali z priorytetów.

- Wątki mogą być łączone w grupy, reprezentowane przez obiekty klasy `ThreadGroup`. Nie jest to zbyt przydatna funkcjonalność. Grupę wątku odczytujemy metodą `getThreadGroup()`.

- Wątki, których przeznaczeniem jest wykonywanie w tle prac pomocniczych, np. odśmiecania, nazywamy demonami. Wątek wskazujemy jako demon metodą `setDaemon()`.

  Zasadnicza różnicą pomiędzy zwykłymi wątkami i wątkami-demonami jest taka, że tak długo,    jak działa jakikolwiek zwykły wątek procesu, proces nie kończy działania. Natomiast       wątki-demony automatycznie kończą działanie wraz z zakończeniem działania procesu.

  Demony są mechanizmem systemowym wspomagającym wykonanie programu. We własnych programach   dodatkowych demonów nie tworzymy.

W pliku [SingleThread.java](https://github.com/Emilo77/SEM5-PW-LAB1/blob/master/SingleThread.java) jest przykład programu jednowątkowego.

### Tworzenie wątków

Każdy proces ma co najmniej jeden wątek, nazywany wątkiem głównym. W programie w Javie definiuje go treść metody `main()`.

Instrukcje wątku, który ma się wykonywać współbieżnie z wątkiem głównym, umieszczamy w metodzie `run()` klasy implementującej interfejs `Runnable`.

Następnie, w programie:

1. tworzymy obiekt `r` tej klasy,

2. tworzymy obiekt `t` klasy `Thread`, przekazując jako argumenty konstruktora obiekt `r` i, opcjonalnie, nazwę wątku,

3. uruchamiamy wątek metodą `start()` obiektu `t`.

Ten sam efekt można osiągnąć nieco prościej, definiując podklasę klasy `Thread` z metodą `run()`. W programie tworzymy obiekt tej klasy i uruchamiamy wątek metodą `start()`.

Nie jest to jednak właściwe rozwiązanie z punktu widzenia paradygmatu obiektowego. Naszym celem nie jest, w tym przypadku, stworzenie szczególnego rodzaju wątku, tylko wskazanie kodu, który ma być wykonany przez wątek. Zamiast dziedziczenia należy więc użyć kompozycji, jak w powyższym opisie.

Zwracamy uwagę, że nie należy uruchamiać wątku metodą `start()` w konstruktorze zdefiniowanej przez nas klasy implementującej `Runnable`.

Gdybyśmy tak zrobili i gdyby ktoś zdefiniował podklasę naszej klasy, to jej konstruktor wykonywałby się współbieżnie z uruchomionym wątkiem. Inaczej mówiąc, wątek działałby na obiekcie, który nie jest jeszcze w pełni skonstruowany. Mogłoby to powodować trudne do wykrycia błędy.

Program [TwoThreads.java](https://github.com/Emilo77/SEM5-PW-LAB1/blob/master/TwoThreads.java) demonstruje uruchamianie drugiego wątku.

Analizując wyniki kolejnego programu przykładowego [TwoWriters.java](https://github.com/Emilo77/SEM5-PW-LAB1/blob/master/TwoWriters.java) możemy zobaczyć efekt działania zarządcy wątków. Tworzone są dwa wątki piszące na standardowe wyjście. Pierwszy z nich wypisuje litery, a drugi cyfry. Widzimy, że oba rodzaje znaków przeplecione są w przypadkowej kolejności.

W programie tym wątek główny oczekuje na zakończenie wątków pomocniczych, wykonując pustą pętlę z warunkiem korzystającym z metody `isAlive()`. Nazywamy to aktywnym oczekiwaniem.

Aktywne oczekiwanie marnuje czas procesora. Na kolejnych zajęciach dowiemy się, jak zrealizować oczekiwanie bez tej wady.

### Ćwiczenie niepunktowane (ManyThreads)

Napisz program `ManyThreads`, który uruchomi liczbę wątków, określoną stałą symboliczną.

Tworzenie wątków należy zrealizować potokowo. Wątek główny uruchomi pierwszy wątek potomny, ten uruchomi drugi itd. aż do ostatniego.

Na zakończenie pracy (tj. po stworzeniu wątku potomnego) każdy wątek powinien wypisać swój numer w potoku.

Spróbuj wpłynąć na kolejność wypisywanych numerów, dodając w wątkach jakieś czynności, których czas będzie zależał od numeru wątku - np. wiele razy losuj liczbę w pętli. Najpierw użyj metody [Math.random()](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Math.html#random()), a następnie porównaj jej działanie z działaniem metody [ThreadLocalRandom.current().nextDouble(1.0)](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/ThreadLocalRandom.html). Skąd wynika ta różnica?

### Zmienne z modyfikatorem `volatile`

Współbieżne wątki mogą mieć dostęp do tych samych zmiennych. Specyfikacja maszyny wirtualnej Javy nie gwarantuje jednak, że wartość zapisana na zmiennej przez jeden wątek stanie się automatycznie widoczna dla pozostałych wątków. Mogą one nadal widzieć poprzednią wartość zmiennej.

Opatrując zmienną modyfikatorem `volatile` informujemy, że wątki odczytujące ją zawsze powinny widzieć aktualną wartość, i że powinna być zachowana kolejność operacji na tej zmiennej wynikająca z kodu programu.

Zastosowanie modyfikatora `volatile` ma jeszcze jeden efekt. Model pamięci języka Java gwarantuje atomowość, czyli niepodzielność, operacji odczytu i zapisu zmiennych, ale tylko dla typów o rozmiarze do 32-bitów włącznie.

W przypadku typów 64-bitowych, czyli `double` i `long`, odczyt i zapis zmiennej może zostać podzielony na dwie operacje 32-bitowe. Może to wpłynąć na wynik programu współbieżnego. Modyfikator `volatile` w deklaracji zmiennej gwarantuje, że do tego nie dojdzie.

Program [TwoWritersSynchronized.java](https://github.com/Emilo77/SEM5-PW-LAB1/blob/master/TwoWritersSynchronized.java) demonstruje użycie zmiennych z modyfikatorem `volatile` do wymiany informacji między wątkami. Wątki są synchronizowane w ten sposób, aby każdy wiersz zawierał znaki tylko jednego rodzaju (tzn. albo litery albo cyfry).

Wykomentowanie `volatile` może, choć nie musi, wpłynąć na wynik programu.

### Ćwiczenie niepunktowane (Primes)

Zdefiniuj klasę `Primes` z metodą statyczną `static boolean isPrime(int n)` sprawdzającą, czy przekazana jako argument liczba całkowita `n` jest liczbą pierwszą.

Metoda zaczyna od sprawdzenia podzielności `n` przez liczby 2, 3, 5, 7, 11, 13, 17, 19, 23, 29. Jeśli `n` nie dzieli się przez żadną z nich, rozpoczyna się obliczenie wielowątkowe.

Tworzonych jest 8 wątków. Każdy bada podzielność `n` przez elementy pewnego ciągu arytmetycznego. Różnica między kolejnymi wyrazami tego ciągu to 30, a wartość początkowa dla każdego z 8 wątków, to, odpowiednio, 31, 37, 41, 43, 47, 49, 53, 59.

Po znalezieniu podzielnika przez jeden z wątków, pozostałe powinny zakończyć pracę. Do synchronizacji wątków wykorzystaj zmienną volatile.

Napisz metodę `main()`, która przetestuje rozwiązanie licząc, ile jest liczb pierwszych od 2 do 10000. Poprawny wynik to 1229.
