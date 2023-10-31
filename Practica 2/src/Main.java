import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static List<Estudiante> estudiantes;

    public static void main(String[] args) throws IOException {
        cargarArchivo(); // Cargar los datos de los estudiantes desde el archivo
        mostrarEstudiantesCar(); // Mostrar estudiantes agrupados por carrera
        totalEstudiantesCar(); // Contar estudiantes por carrera
        totalMujeresyHombres(); // Contar estudiantes por género y carrera
        estudiantePuntajeMasAlto(); // Encontrar al estudiante con el puntaje más alto en matemáticas
        estudiantePuntajeMasAltoCar(); // Encontrar al estudiante con el puntaje más alto en matemáticas por carrera
        puntaPromedioPorCar(); // Calcular el promedio de puntajes por carrera
    }

    static void cargarArchivo() throws IOException {
        // Función encargada de leer línea por línea el archivo estudiantes.csv y guardar las partes necesarias en objetos Estudiante
        Pattern pattern = Pattern.compile(",");
        String filename = "estudiantes.csv";

        try (Stream<String> lines = Files.lines(Path.of(filename))) {
            estudiantes = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new Estudiante(Integer.parseInt(arr[0]), arr[1], arr[2], arr[4], arr[9], Integer.parseInt(arr[10]));
            }).collect(Collectors.toList());
        }
    }

    static void mostrarEstudiantesCar() {
        // Función que muestra estudiantes agrupados por carrera
        System.out.printf("%nEstudiantes por Carrera:%n");
        Map<String, List<Estudiante>> agrupadoPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Estudiante::getCareer_aspiration));
        agrupadoPorCarrera.forEach(
                (carrera, estudiantesEnCarrera) -> {
                    System.out.println("\n" + carrera + ":\n");
                    estudiantesEnCarrera.forEach(
                            estudiante -> System.out.printf(" %s%n", estudiante));
                }
        );
    }

    static void totalEstudiantesCar() {
        // Función que cuenta la cantidad de estudiantes por carrera
        System.out.printf("%nConteo de estudiantes por carrera:%n");
        Map<String, Long> conteoDeEstudiantesPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Estudiante::getCareer_aspiration,
                                TreeMap::new, Collectors.counting()));
        conteoDeEstudiantesPorCarrera.forEach(
                (carrera, conteo) -> System.out.printf(
                        "La cantidad de estudiantes de %s es: %d%n", carrera, conteo));
    }

    static void totalMujeresyHombres() {
        // Función que cuenta la cantidad de estudiantes por género y carrera
        System.out.printf("%nConteo de géneros en la carrera:%n");

        Map<String, Map<String, Long>> conteoDeEstudiantesPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(
                                Estudiante::getCareer_aspiration,
                                Collectors.groupingBy(Estudiante::getGender, Collectors.counting())
                        ));

        conteoDeEstudiantesPorCarrera.forEach((carrera, conteoPorGenero) -> {
            System.out.println("Carrera: " + carrera);
            conteoPorGenero.forEach((genero, conteo) -> {
                System.out.printf("Género %s: %d%n", genero, conteo);
            });
            System.out.println("");
        });
    }

    static void estudiantePuntajeMasAlto() {
        // Función que encuentra a los estudiante con el puntaje más alto en matemáticas
        System.out.printf("%nEstudiantes con mejor calificación:%n");
        int maxScore = estudiantes.stream()
                .mapToInt(Estudiante::getMath_score)
                .max()
                .orElse(0);
        estudiantes.stream()
                .filter(estudiante -> estudiante.getMath_score() == maxScore)
                .forEach(System.out::println);
    }

    static void estudiantePuntajeMasAltoCar() {
        // Función que encuentra al estudiante con el puntaje más alto en matemáticas por carrera
        System.out.printf("%nEstudiantes con mejor calificación por carrera:%n");
        Map<String, Map<Integer, List<Estudiante>>> estudiantesPorCarreraYCalificacion =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(
                                Estudiante::getCareer_aspiration,
                                Collectors.groupingBy(Estudiante::getMath_score)
                        ));

        estudiantesPorCarreraYCalificacion.forEach((carrera, calificacionPorEstudiantes) -> {
            System.out.println("\nCarrera: " + carrera + "\n");
            calificacionPorEstudiantes.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getKey))
                    .ifPresent(entry -> {
                        int maxScore = entry.getKey();
                        List<Estudiante> estudiantesConMaxPuntaje = entry.getValue();
                        System.out.println("Máxima calificación en matemáticas: " + maxScore);
                        System.out.println("Estudiantes con esta calificación:");
                        estudiantesConMaxPuntaje.forEach(estudiante -> System.out.println(estudiante.toString()));
                    });
        });
    }

    static void puntaPromedioPorCar() {
        // Función que calcula el promedio de puntajes por carrera
        System.out.printf("%nConteo de promedio por carrera:%n \n");

        Map<String, List<Estudiante>> conteoDeEstudiantesPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(
                                Estudiante::getCareer_aspiration
                        ));

        conteoDeEstudiantesPorCarrera.forEach((carrera, estudiantesEnCarrera) -> {
            System.out.println("Carrera: " + carrera);
            System.out.println(estudiantesEnCarrera.stream().mapToDouble(Estudiante::getMath_score).average().getAsDouble());
            System.out.println("");
        });
    }
}