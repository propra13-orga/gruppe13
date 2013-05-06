

Aufbau der raumX.txt damit sie vom Spiel gelesen werden können:
aktuelle Größe: 24x15 Felder (22x13 begehbar, und an den Rändern Platz um Türen in die Wände einfügen zu können)
Zeichen:
	-X = Rand des Spielfeldes, nicht begehbar
	-D = Tür, wechselt den Raum
	/*-F = Spawnpunkt der Spielfigur (nur für "Startraum" Relevant, sonst in der Tür)*/ //nichtmehr genutzt
	-W = Hinderniss ("Wall"), nicht begehbar
	-E = Spawnpunkt Gegner, bisher nur ein Gegnertyp, später durch mehr Zeichen Gegnertyp spezifizierbar
	-0 = "Nichts", freier Raum, begehbar
