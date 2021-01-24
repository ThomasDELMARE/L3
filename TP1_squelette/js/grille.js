/* Classe principale du jeu, c'est une grille de cookies. Le jeu se joue comme
Candy Crush Saga etc... c'est un match-3 game... */
class Grille {

  tabCookiesCliquees = [];
  tabCookies = [];

  nbLignes;
  nbColonnes;
  nbDeCookiesDifferents;


  constructor(nbLignes, nbColonnes, nbDeCookiesDifferents) {
    this.nbLignes = nbLignes;
    this.nbColonnes = nbColonnes;
    this.nbDeCookiesDifferents = nbDeCookiesDifferents;

    this.remplirTableauDeCookies(nbDeCookiesDifferents);
  }

  /**
   * parcours la liste des divs de la grille et affiche les images des cookies
   * correspondant à chaque case. Au passage, à chaque image on va ajouter des
   * écouteurs de click et de drag'n'drop pour pouvoir interagir avec elles
   * et implémenter la logique du jeu.
   */
  showCookies() {

    let caseDivs = document.querySelectorAll("#grille div");

    caseDivs.forEach((div, index) => {
      // remplissage du tableau
      let ligne = Math.floor(index / this.nbColonnes);
      let colonne = index % this.nbColonnes;

      // console.log("On remplit le div index=" + index + " l=" + ligne + " col=" + colonne);

      let img = this.tabCookies[ligne][colonne].htmlImage;

      // on add les actions que la grille écoutera lorsqu'il y aura un click sur une image
      img.addEventListener("click", (event) => {
        let clickedImg = event.target;
        let l = clickedImg.dataset.ligne;
        let c = clickedImg.dataset.colonne;
        let cookieCliquee = this.tabCookies[l][c];
        cookieCliquee.selectionnee();

        // PARTIE GERANT LE SWAP

        // cas où aucun cookie n'a été selectionné
        if (this.tabCookiesCliquees.length == 0) {
          this.tabCookiesCliquees.push(cookieCliquee);
        }
        // cas où un cookie a été sélectionné, on traite le deuxieme cookie (MAX)
        else if (this.tabCookiesCliquees.length == 1) {
          this.tabCookiesCliquees.push(cookieCliquee);

          // on vérifie si la distance entre les cookies permet le swap
          if (this.swapPossible()) {
            console.log("swap possible !");
            this.swapCookies();
          }
          else {
            console.log("swap impossible :(");
          }

          this.tabCookiesCliquees[0].deselectionnee();
          this.tabCookiesCliquees[1].deselectionnee();

          this.tabCookiesCliquees = [];
        }
      });

      // PARTIE GERANT LE DRAG N DROP

      img.ondragstart = (evt) => {
        console.log("dragstart");
        let clickedImg = event.target;
        let l = clickedImg.dataset.ligne;
        let c = clickedImg.dataset.colonne;
        let cookieDragged = this.tabCookies[l][c];

        this.tabCookiesCliquees = [];
        this.tabCookiesCliquees.push(cookieDragged);
        cookieDragged.selectionnee();
      }

      // il ne se passe rien quand on passe un cookie au-dessus des autres
      img.ondragover = (evt) => {
        return false;
      }

      // on change le visuel (css) quand on entre dans une case
      img.ondragenter = (evt) => {
        console.log("ondragenter");
        let img = evt.target;
        // on ajoute la classe CSS grilleDragOver
        img.classList.add("grilleDragOver");
      }

      // on change le visuel (css) quand on sort d'une case
      img.ondragleave = (evt) => {
        console.log("ondragleave");
        let img = evt.target;
        // on enlève la classe CSS grilleDragOver
        img.classList.remove("grilleDragOver");
      };

      // on recuperer le cookie sur lequel on a lache l'autre cookie pour effectuer les actions liées
      img.ondrop = (evt) => {
        console.log("ondrop");

        let imgCible = evt.target;
        let l = imgCible.dataset.ligne;
        let c = imgCible.dataset.colonne;
        let cookieCible = this.tabCookies[l][c];

        this.tabCookiesCliquees.push(cookieCible);

        // on vérifie si la distance entre les cookies permet le swap
        if (this.swapPossible()) {
          console.log("swap possible !");
          this.swapCookies();
        }
        else {
          console.log("swap impossible :(");
        }

        this.tabCookiesCliquees[0].deselectionnee();
        this.tabCookiesCliquees[1].deselectionnee();
        imgCible.classList.remove("grilleDragOver");

        this.tabCookiesCliquees = [];
      }

      // on affiche l'image dans le div pour la faire apparaitre à l'écran.
      div.appendChild(img);
    });
  }

  getCookieFromImage(image) {
    let [l, c] = Cookie.getLigneColonneFromImage(image);
    console.log([l, c]);
    return this.tabCookies[l][c];
  }

  /**
   * Initialisation du niveau de départ. Le paramètre est le nombre de cookies différents
   * dans la grille. 4 types (4 couleurs) = facile de trouver des possibilités de faire
   * des groupes de 3. 5 = niveau moyen, 6 = niveau difficile
   *
   * Améliorations : 1) s'assurer que dans la grille générée il n'y a pas déjà de groupes
   * de trois. 2) S'assurer qu'il y a au moins 1 possibilité de faire un groupe de 3 sinon
   * on a perdu d'entrée. 3) réfléchir à des stratégies pour générer des niveaux plus ou moins
   * difficiles.
   *
   * On verra plus tard pour les améliorations...
   */
  remplirTableauDeCookies(nbDeCookiesDifferents) {
    let type;
    this.tabCookies = create2DArray(this.nbLignes);

    for (let i = 0; i < this.nbLignes; i++) {
      for (let j = 0; j < this.nbColonnes; j++) {
        type = Math.floor(nbDeCookiesDifferents * Math.random());
        this.tabCookies[i][j] = new Cookie(type, i, j);
      }
    }

    // this.detecteAlignements();
  }


  detecteAlignements() {
    this.nbAlignements = 0;

    // pour chaque ligne on va appeler detecteAlignementLigne et idem pour chaque colonne
    for (let l = 0; l < this.nbLignes; l++) {
      this.detecteAlignementLigne(l);
    }

    for (let c = 0; c < this.nbColonnes; c++) {
      this.detecteAlignementColonne(c);
    }

    console.log(this.nbAlignements);

    return this.nbAlignements !== 0;
  }

  detecteAlignementLigne(ligne) {
    // on va parcourir la ligne et voir si on des alignements
    let ligneGrille = this.tabCookies[ligne];

    //console.log(ligneGrille); // ok ça, c'est le tableau des cookies sur la ligne
    // on va le parcourir de l'index 0 à l'index 6 inclu (this.nbColonnes -3);le dernier
    // triplet testé sera composé des cookies aux indexes 6, 7 et 8 (on va de 0 à 9)

    for (let l = 0; l <= this.nbColonnes - 3; l++) {
      //console.log("Je teste les indexes " + l + " " + (l + 1) + " " + (l + 2));
      let c1 = ligneGrille[l];
      let c2 = ligneGrille[l + 1];
      let c3 = ligneGrille[l + 2];

      if (c1.type === c2.type && c1.type === c3.type) {
        // on marque les trois
        c1.selectionnee();
        c2.selectionnee();
        c3.selectionnee();

        /*
        // on les supprime
        c1.supprimer();
        c2.supprimer();
        c3.supprimer();
        */

       ligneGrille[l] = new Cookie(Math.floor(this.nbDeCookiesDifferents * Math.random()), c1.ligne, c1.colonne);
       ligneGrille[l + 1] = new Cookie(Math.floor(this.nbDeCookiesDifferents * Math.random()), c2.ligne, c2.colonne);
       ligneGrille[l + 2] = new Cookie(Math.floor(this.nbDeCookiesDifferents * Math.random()), c3.ligne, c3.colonne);

       this.detecteAlignementLigne();

        this.nbAlignements++;
      }
    }
  }

  detecteAlignementColonne(colonne) {
    // on veut afficher les cookies situées sur une colonne donnée
    for (let ligne = 0; ligne <= this.nbLignes - 3; ligne++) {
      //console.log("On va examiner la case " + ligne + " " + colonne);
      let c1 = this.tabCookies[ligne][colonne];
      let c2 = this.tabCookies[ligne + 1][colonne];
      let c3 = this.tabCookies[ligne + 2][colonne];

      if (c1.type === c2.type && c1.type === c3.type) {
        // on marque les trois
        c1.selectionnee();
        c2.selectionnee();
        c3.selectionnee();

        /*
        // on les supprime
        c1.supprimer();
        c2.supprimer();
        c3.supprimer();
        */

        this.tabCookies[ligne][colonne] = new Cookie(Math.floor(this.nbDeCookiesDifferents * Math.random()), c1.ligne, c1.colonne);
        this.tabCookies[ligne + 1][colonne] = new Cookie(Math.floor(this.nbDeCookiesDifferents * Math.random()), c2.ligne, c2.colonne);
        this.tabCookies[ligne + 2][colonne] = new Cookie(Math.floor(this.nbDeCookiesDifferents * Math.random()), c3.ligne, c3.colonne);

        this.detecteAlignementColonne();

        this.nbAlignements++;
      }
    }
  }

  swapPossible() {
    console.log(Cookie.distance(this.tabCookiesCliquees[0], this.tabCookiesCliquees[1]) == 1);
    return Cookie.distance(this.tabCookiesCliquees[0], this.tabCookiesCliquees[1]) == 1;
  }

  swapCookies() {
    console.log("SWAP C1 C2");
    // On charge les deux cookies
    let c1 = this.tabCookiesCliquees[0];
    let c2 = this.tabCookiesCliquees[1];

    // On échange leurs images et types    
    let image2 = c2.htmlImage.src;
    let type2 = c2.type;

    c2.htmlImage.src = c1.htmlImage.src;
    c2.type = c1.type;

    c1.htmlImage.src = image2;
    c1.type = type2;

    this.detecteAlignements();
  }

  dragNDrop() {

  }

}
