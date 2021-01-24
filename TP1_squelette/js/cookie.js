class Cookie {
  static urlsImagesNormales = [
    "./assets/images/Croissant@2x.png",
    "./assets/images/Cupcake@2x.png",
    "./assets/images/Danish@2x.png",
    "./assets/images/Donut@2x.png",
    "./assets/images/Macaroon@2x.png",
    "./assets/images/SugarCookie@2x.png",
  ];
  static urlsImagesSurlignees = [
    "./assets/images/Croissant-Highlighted@2x.png",
    "./assets/images/Cupcake-Highlighted@2x.png",
    "./assets/images/Danish-Highlighted@2x.png",
    "./assets/images/Donut-Highlighted@2x.png",
    "./assets/images/Macaroon-Highlighted@2x.png",
    "./assets/images/SugarCookie-Highlighted@2x.png",
  ];

  type;
  ligne;
  colonne;
  htmlImage;

  constructor(type, ligne, colonne) {
    this.type = type;
    this.ligne = ligne;
    this.colonne = colonne;

    this.htmlImage = document.createElement('img');
    this.htmlImage.src = Cookie.urlsImagesNormales[this.type];
    this.htmlImage.width = 80;
    this.htmlImage.height = 80;
    this.htmlImage.dataset.ligne = this.ligne;
    this.htmlImage.dataset.colonne = this.colonne;
  }

  selectionnee() {
    // on change l'image et la classe CSS
    this.htmlImage.classList.add("cookies-selected");
    this.htmlImage.src = Cookie.urlsImagesSurlignees[this.type];
  }

  deselectionnee() {
    // on change l'image et la classe CSS
    this.htmlImage.classList.remove("cookies-selected");
    this.htmlImage.src = Cookie.urlsImagesNormales[this.type];
  }

  static swapCookies(c1, c2) {
    console.log("SWAP C1 C2");
    // On échange leurs images et types    
    image2 = c2.htmlImage;
    type2 = c2.type;

    c2.htmlImage = c1.htmlImage;
    c2.type = cookie1.colonne;

    c1.htmlImage = image2;
    c1.type = type2;

    // et on remet les désélectionne
    c1.deselectionnee();
    c2.deselectionnee();
  }

  /** renvoie la distance entre deux cookies */
  static distance(cookie1, cookie2) {
    let l1 = cookie1.ligne;
    let c1 = cookie1.colonne;
    let l2 = cookie2.ligne;
    let c2 = cookie2.colonne;

    const distance = Math.sqrt((c2 - c1) * (c2 - c1) + (l2 - l1) * (l2 - l1));
    console.log("Distance = " + distance);
    return distance;
  }

  static getLigneColonneFromImage(img){
    return [img.dataset.ligne, img.dataset.colonne];
  }

  supprimer() {
    this.htmlImage.classList.add("cookie-cachee");
  }
}
