package hei.devweb.entities;

public enum Instrument {

            Alto (1),
            Batterie(2),
            Contrebasse(3),
            Guitare(4),
            Piano (5),
            Saxophone(6),
            Trombone(7),
            Trompette(8),
            Autre(9);


    Integer id;


    Instrument(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
