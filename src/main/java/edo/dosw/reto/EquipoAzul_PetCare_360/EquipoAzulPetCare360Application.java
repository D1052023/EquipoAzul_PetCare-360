package edo.dosw.reto.EquipoAzul_PetCare_360;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "edo.dosw.reto")
@EnableMongoRepositories(basePackages = "edo.dosw.reto.repositories")
public class EquipoAzulPetCare360Application {

    public static void main(String[] args) {
        SpringApplication.run(EquipoAzulPetCare360Application.class, args);
    }
}
