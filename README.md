Sustav za praèenje ocjena u školama!

1. Downloadat Git Bash https://git-scm.com/downloads

2. Otvoriti Git Bash i izvršiti sljedeæe naredbe(samo kod prvog pokretanja Git Basha):
	$git config --global user.name "userNameame"
	$git config --global user.email "userEmail"

3. U Git Bashu pozicionirati se u direktorij projekta.

4. Kreiranje Git repozitorija u direktoriju projekta(potrebno samo prvi put):
	$git init

5. Kloniranje projekta u direktorij projekta:
	$git clone URL
	
6. Kada želite commitati promjene izvršiti sljedeæe naredbe:
	$git pull (provjera verzije projekta)
	$git add . ili $git add fileName
	$git commit -m "Ovdje ide komentar što je dodano i koje su promjene"
	$git push ili $git push origin
	