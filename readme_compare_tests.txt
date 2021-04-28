compare_tests.exe

A file futtatásához szükséges:
	-'input_files' nevű mappa, amiben az adott tesztekhez tartozó bemenetek találhatóak.
	-'output_files' nevű mappa, amiben az adott tesztekhez tartozó elvárt kimenetek találhatóak.
A file futtatásának eredménye:
	-'de_facto_output_files' nevű mappa, amelyben az egyes tesztekhez tartozó tényleges kimenetek találhatóak.
	-'test_results.txt' nevű file, amelyben soronként a két összehasonlított file és mellettük az összehasonlítás
	 eredménye található amely "tests are the same." ha megegyeznek, és "tests are not the same." ha különböznek.



Mit ír be a cmd-be?(szóljatok ha ez nem jó és írjam át)
-"javac src/*.java -d bin"
majd minden tesztesetre:
"java -cp bin Skeleton input_files_mappabol_soronkovetkezo teljes_utvonal_es_nev_kiterjesztessel_hogy_hova_mentse_a_kimenetet"