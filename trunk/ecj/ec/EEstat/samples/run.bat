:ES
set repeat = 20
set dir=ES
(set function=Sphere%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat 
-p pop.subpop.0.species.mutation-stdev = 0.1)

(set function=)
(set function=Schwefel%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat
-p pop.subpop.0.species.mutation-stdev = 7.1)

(set function=)
(set function=Rastrigin%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat
-p pop.subpop.0.species.mutation-stdev = 0.05)

(set function=)
(set function=Rosenbrock%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat
-p pop.subpop.0.species.mutation-stdev = 0.36)

:PSO
set dir=PSO
(set function=Sphere%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat
-p pop.subpop.0.velocity-multiplier = 0.31)

(set function=)
(set function=Schwefel%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat
-p pop.subpop.0.velocity-multiplier = 0.41)


(set function=)
(set function=Rastrigin%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat
-p pop.subpop.0.velocity-multiplier = 4.21)

(set function=)
(set function=Rosenbrock%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat
-p pop.subpop.0.velocity-multiplier = 0.21)

:DE
set dir=DE
(set function=SphereBest%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat)

(set function=)
(set function=SchwefelBest%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat)


(set function=)
(set function=RastriginBest%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat)

(set function=)
(set function=RosenbrockBest%dir%)
for /l %%X in (1,1,%repeat%) do (
java ec.Evolve -file %dir%\%function%.param -p stat.child.0.stat-file=%function%EEstat_b%%X.stat -p stat.file=%function%Out%%X.stat)