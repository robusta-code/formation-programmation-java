package fr.lteconsulting;

// TODO : exercice supplémentaire : rendre variable le nombre de lignes et de colonnes
public class Puissance4
{
	private Plateau<Jeton> plateau;

	public Puissance4()
	{
		plateau = new Plateau<>( 7, 6 );
	}

	public void tester()
	{
		for( int i = 0; i < 4; i++ )
			plateau.placer( new Jeton( CouleurPuissance4.Jaune ), new Coordonnee( 0, i ) );

		System.out.println( aucunGagnant() );
	}

	public void jouer()
	{
		String nomRouge = Saisie.saisie( "Nom du joueur Rouge" );
		String nomJaune = Saisie.saisie( "Nom du joueur Jaune" );

		JoueurPuissance4[] joueurs = new JoueurPuissance4[2];
		joueurs[0] = new JoueurPuissance4( CouleurPuissance4.Rouge, nomRouge );
		joueurs[1] = new JoueurPuissance4( CouleurPuissance4.Jaune, nomJaune );

		System.out.println( "C'est parti !" );

		int tour = 0;

		while( plateau.possedeCasesVides() && aucunGagnant() )
		{
			plateau.afficher();

			JoueurPuissance4 joueur = joueurs[tour % 2];

			int ligneInsertion;
			int colonneInsertion;
			do
			{
				colonneInsertion = Saisie.saisieInt( "A quelle colonne jouez vous " + joueur.getNom() + " ?" );

				// les index commencent à 1 dans la tête de l'utilisateur
				colonneInsertion = colonneInsertion - 1;

				ligneInsertion = getPremiereLigneVide( colonneInsertion );
				if( ligneInsertion < 0 )
					System.out.println( "NON !!! Pas possible, cette colonne est pleine !!!" );
			}
			while( ligneInsertion < 0 );

			plateau.placer( new Jeton( joueur.getCouleur() ), new Coordonnee( colonneInsertion, ligneInsertion ) );

			tour += 1;
		}
	}

	private boolean aucunGagnant()
	{
		for( int x = 0; x < 7; x++ )
			for( int y = 0; y < 6; y++ )
				if( positionVictorieuse( new Coordonnee( x, y ) ) )
					return false;

		return true;
	}

	private boolean positionVictorieuse( Coordonnee origine )
	{
		Jeton jeton = plateau.getPieceAt( origine );
		if( jeton == null )
			return false;

		CouleurPuissance4 couleur = jeton.getCouleur();

		// combien à gauche du meme ?
		// combien à droite du meme ?
		if( combien( origine, couleur, -1, 0 ) + combien( origine, couleur, 1, 0 ) >= 3 )
			return true;

		// combien en haut du même ?
		// combien en bas du même ?
		if( combien( origine, couleur, 0, -1 ) + combien( origine, couleur, 0, 1 ) >= 3 )
			return true;

		// combien HG du meme
		// combien BD du meme
		if( combien( origine, couleur, -1, -1 ) + combien( origine, couleur, 1, 1 ) >= 3 )
			return true;

		// combien BG
		// combien HD
		if( combien( origine, couleur, 1, -1 ) + combien( origine, couleur, -1, 1 ) >= 3 )
			return true;

		return false;
	}

	/**
	 * Combien y a-t-il de piece consécutives de la couleur spécifiée à partir de la coordonnée (exclue) ?
	 * 
	 * @param origine
	 * @param couleur
	 * @param pasHorizontal
	 * @param pasVertical
	 * @return
	 */
	private int combien( Coordonnee origine, CouleurPuissance4 couleur, int pasHorizontal, int pasVertical )
	{
		int x = origine.getX();
		int y = origine.getY();

		int nb = 0;

		do
		{
			x += pasHorizontal;
			y += pasVertical;

			if( (x < 0) || (x > 6) || (y < 0) || (y > 5) )
				break;

			Jeton jeton = plateau.getPieceAt( new Coordonnee( x, y ) );

			// condition d'arrêt
			if( jeton == null || jeton.getCouleur() != couleur )
				break;
			
			nb++;

		}
		while( true );

		return nb;
	}

	private int getPremiereLigneVide( int colonne )
	{
		for( int ligne = 5; ligne >= 0; ligne-- )
		{
			if( plateau.getPieceAt( new Coordonnee( colonne, ligne ) ) == null )
			{
				return ligne;
			}
		}

		return -1;
	}
}
