
set dir=BFA
(set function=Rosenbrock%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\RosenbrockBFAElim.param -p stat.child.0.stat-file=%function%ElimEEstat%%X.stat -p stat.file=%function%BFAOut%%X.stat)

set dir=BFA
(set function=Sphere%dir%)
for /l %%X in (1,1,9) do (
java ec.Evolve -file %dir%\SphereBFAElim.param -p stat.child.0.stat-file=%function%ElimEEstat%%X.stat -p stat.file=%function%BFAOut%%X.stat)