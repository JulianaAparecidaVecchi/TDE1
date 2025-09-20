module tde {
        requires javafx.controls;
        requires javafx.fxml;
        requires org.kordamp.bootstrapfx.core;
        requires java.desktop;

        opens aplicacao to javafx.fxml;
        opens algoritmos to javafx.fxml;

        exports aplicacao;
        exports algoritmos;
}
