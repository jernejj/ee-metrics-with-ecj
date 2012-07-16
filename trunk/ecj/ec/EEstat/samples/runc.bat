
set dir=ES
(set function=Sphere%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p pop.subpop.0.species.mutation-stdev=0.05)

(set function=)
(set function=Schwefel%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p pop.subpop.0.species.mutation-stdev=3.1)

(set function=)
(set function=Rastrigin%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p pop.subpop.0.species.mutation-stdev=0.009)

(set function=)
(set function=Rosenbrock%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p pop.subpop.0.species.mutation-stdev=0.1)

:PSO
set dir=PSO
(set function=Sphere%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p pop.subpop.0.velocity-multiplier=0.11)

(set function=)
(set function=Schwefel%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p pop.subpop.0.velocity-multiplier=0.21)


(set function=)
(set function=Rastrigin%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p pop.subpop.0.velocity-multiplier=1.21)

(set function=)
(set function=Rosenbrock%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p pop.subpop.0.velocity-multiplier=0.12)

:DE
set dir=DE
(set function=SphereBest%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p breed.f=0.24 -p breed.cr=0.12)

(set function=)
(set function=SchwefelBest%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p breed.f=0.577 -p breed.cr=0.73)

(set function=)
(set function=RastriginBest%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p breed.f=0.28 -p breed.cr=0.76)

(set function=)
(set function=RosenbrockBest%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_c%%X.stat -p stat.file=%function%Outc%%X.stat -p breed.f=0.68 -p breed.cr=0.28)