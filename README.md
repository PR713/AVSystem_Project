# Symulacja Inteligentnego Zarządzania Sygnalizacją Świetlną na Skrzyżowaniu

## Opis Projektu

Projekt symuluje ruch drogowy na skrzyżowaniu z sygnalizacją świetlną. System zarządza pojazdami, które poruszają się w różnych kierunkach, oraz steruje sygnalizacją świetlną, aby minimalizować kolizje i optymalizować przepływ ruchu. Symulacja obsługuje różne strategie sterowania sygnalizacją świetlną, takie jak **strategia stałego cyklu** oraz **strategia zależna od czasu oczekiwania**.

## Główne Funkcjonalności

1. **Dodawanie pojazdów**: Pojazdy są dodawane do symulacji z określonym kierunkiem startowym i docelowym za pomocą pliku JSON.
2. **Sterowanie sygnalizacją świetlną**: System zarządza sygnalizacją świetlną na skrzyżowaniu, aby umożliwić pojazdom bezpieczne przejazdy.
3. **Strategie sterowania**:
   - **FixedCycleStrategy**: Sygnalizacja zmienia się w stałych odstępach czasu, niezależnie od natężenia ruchu.
   - **TimeDependentStrategy**: Sygnalizacja zmienia się priorytetowo w zależności od czasu oczekiwania pojazdów.
4. **Symulacja kroku**: W każdym kroku symulacji  pierwsze pojazdy na kierunkach z zielonym światłem są przepuszczane przez skrzyżowanie.
5. **Zapisywanie wyników**: Wynikiem symulacji jest lista pojazdów, które opuściły skrzyżowanie w danym kroku zapisana do pliku JSON.

## Jak Działa Algorytm?

### FixedCycleStrategy
- Sygnalizacja zmienia się w stałych odstępach czasu.
- Kierunki z zielonym światłem są wybierane losowo spośród tych, na których czekają pojazdy. Najpierw o ile to możliwe, próbuje przepuszczać pojazdy z dwóch kierunków jednocześnie - jeśli jest to niemożliwe, to wybiera losowo pojedynczy kierunek z kierunków, na których stoją pojazdy.
- Algorytm unika kolizji poprzez sprawdzanie, czy pojazdy na przeciwnych kierunkach mogą bezpiecznie przejechać.

### TimeDependentStrategy
- Sygnalizacja zmienia się w zależności od czasu oczekiwania pojazdów. Priorytetem również jest przepuszczanie pojazdów z dwóch kierunków jednocześnie ale z uwzględnieniem czasu oczekiwania. Jeśli powyższe nie jest możliwe, przepuszczane są pojazdy na pojedynczych kierunkach.
- Priorytet mają pojazdy, które czekają najdłużej.
- Algorytm wybiera kierunki z zielonym światłem na podstawie najdłuższego czasu oczekiwania i unika kolizji.

### Zarządzanie Kolizjami
- Algorytm sprawdza, czy pojazdy na przeciwnych lub sąsiednich kierunkach mogą bezpiecznie przejechać bez kolizji.
- Jeśli kolizja jest możliwa, sygnalizacja jest ustawiana tak, aby umożliwić przejazd tylko jednego z kierunków.

## Wydajność Systemu

- System jest zoptymalizowany pod kątem zarządzania ruchem.
- Wykorzystanie `ScheduledExecutorService` pozwala na efektywne zarządzanie czasem zmian świateł i emuluje czas przejazdu pojazdów przez skrzyżowanie, co może być wykorzystane przy dalszym rozwoju projektu w GUI.

## Scenariusze Ruchu

1. **Niskie natężenie ruchu**: System efektywnie zarządza pojazdami, minimalizując czas oczekiwania (strategia czasu oczekiwania).
2. **Wysokie natężenie ruchu**: System priorytetyzuje kierunki z największym natężeniem ruchu, aby zminimalizować korki (strategia czasu oczekiwania).
3. **Równoważne natężenie ruchu na wszystkich kierunkach**: System równomiernie rozdziela czas zielonego światła, aby zapewnić sprawiedliwe przejazdy (strategia czasu oczekiwania).

## Organizacja Kodu

- **Modułowość**: Kod jest podzielony na moduły, takie jak `model`, `trafficstrategy`, `enums`, co ułatwia zarządzanie i rozszerzanie funkcjonalności.
- **Czytelność**: Kod jest dobrze udokumentowany, a nazwy zmiennych i metod są opisowe.
- **Zrozumiałość**: Logika sterowania sygnalizacją świetlną jest czytelna.

## Pokrycie Testami

- Projekt zawiera niezbędne testy jednostkowe dla kluczowych komponentów, takich jak `TrafficLights`, `Intersection` oraz strategie sterowania sygnalizacją.
- Testy sprawdzają poprawność działania algorytmów w różnych scenariuszach ruchu.

## Dodatkowe Funkcjonalności

- **Dynamiczne zarządzanie sygnalizacją**: System może być łatwo rozszerzony o nowe strategie sterowania sygnalizacją świetlną.
- **Zapisywanie wyników**: Wyniki symulacji są zapisywane do pliku, co umożliwia analizę po zakończeniu symulacji.
- **Elastyczność**: System może być dostosowany do różnych konfiguracji skrzyżowań i scenariuszy ruchu.

## Jak Uruchomić Projekt?

1. Sklonuj repozytorium:
```bash
   git clone https://github.com/PR713/AVSystem_Project
```

2. W terminalu uruchom polecenie:

```bash
-- strategia stałego cyklu
./gradlew run --args="src/main/resources/input.json src/main/resources/output.json fixed"

-- strategia czasu oczekiwania
./gradlew run --args="src/main/resources/input.json src/main/resources/output.json time"
```