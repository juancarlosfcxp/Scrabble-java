package practicascrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programa que permite jugar una variante simplificada del Scrabble.
 * que consiste en intentar formar palabras utilizando las letras de un array de char y que sean validas dentro del diccionario
 *cada letra tiene una puntuacion que otorgan una puntuacion final a la palabra
 * en el programa es posible jugar en solitario y contra elordenador
 * @author CARLOS FLORES
 * @version 2.0 30/03/2019
 */
public class PracticaScrabble {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner entrada = new Scanner(System.in, "ISO-8859-1");
        int lengua = menuLenguaje();
        String[] diccionario = cargarPalabras(lengua);
        String palOrdenador, palJugador;
        int jugar = 0, partidas = 1;
        do {
            int pJug = 0, pOrd = 0;
            System.out.println("________________________________________________________");
            int modo = menuModoJuego(lengua);
            if (modo == 2) {
                partidas = 3;
            }
            do {
                char[] letras = generarArrayLletres(lengua);
                palJugador = solicitarPalabra(diccionario, letras, lengua);
                pJug = pJug + calcularPuntuacio(palJugador, lengua);
                String[] textJug = {"The punctuation for your word is: ", "La puntuacion para tu palabra es: ", "La puntuació per la teva paraula és: "};
                System.out.println(textJug[lengua] + calcularPuntuacio(palJugador, lengua));
                if (modo == 2) {
                    palOrdenador = millorParaula(diccionario, letras, lengua);
                    pOrd = pOrd + calcularPuntuacio(palOrdenador, lengua);
                }
                partidas--;
                System.out.println("");
            } while (partidas > 0);
            if (modo == 2) {
                resultado(pJug, pOrd, lengua);
            }
            jugar = menuJugarOAcabar(lengua);
        } while (jugar != 2);
    }

    /**
     * Funcion filtro de formato que recibe un mensaje que se printarar mientras
     * la opcion no cumpla los parametros
     *
     * @param mensaje
     * @param lengua
     * @return int[opcion]
     */
    public static int filtroFormato(String mensaje, int lengua) {
        Scanner entrada = new Scanner(System.in);
        int opcion = 0;
        boolean ok;
        String[] errores = {"Incorrect entry, please enter a valid option",
            "Entrada incorrecta, porfavor introduce una opcion valida",
            "Entrada incorrecta, si us plau introdueix una opció valida", ""};
        do {
            System.out.println(mensaje);
            ok = true;
            try {
                String num = entrada.next();
                opcion = Integer.parseInt(num);
            } catch (NumberFormatException ex) {
                ok = false;
                System.out.println(errores[lengua]);
            }
        } while (!ok);
        return opcion;
    }
    
    /**
      *Funcion que pide al usuario la lengua castellano [0] ,catalan [1] o ingles [2]
      *Reutilizamos la funcion filtroFormato
      * @return int lengua
      */
    public static int menuLenguaje() {
        Scanner entrada = new Scanner(System.in);
        int opcion = 1;
        String mensaje = "Welcome to Scrabble Choose a Language /\nBienvenido a Scrabble Escoge una Lengua /\nBenvingut a Scrabble Escull una Llengua ??"
                + "\nOption 0: English\nOpcion 1: Castellano \nOpció  2: Català";
        do {
            opcion = filtroFormato(mensaje, 3);
        } while (opcion != 0 && opcion != 1 && opcion != 2);
        return opcion;
    }

    /**
     * Funcion que muestra un menu para seleccionar modo de juego.
     *
     * @param lengua
     * @return int dificultad
     */
    public static int menuModoJuego(int lengua) {
        Scanner entrada = new Scanner(System.in);
        int opcion = 0;
        String[] mensaje = {"Choose an Option\n1: Single player\n2: vs Computer (several rounds)",
            "Escoje una opcion \n1: Jugar solo\n2: vs Ordenador(varias rondas)",
            "Escull una opcio\n1: Jugar sol \n2: vs Ordinador(diverses rondes)"};
        do {
            opcion = filtroFormato(mensaje[lengua], lengua);
        } while (opcion != 1 && opcion != 2);
        return opcion;
    }
    
    /**
     * Funcion que muestra un menu para seleccionar si volver a jugar o salir.
     *
     * @param lengua
     * @return int opcion
     */
    public static int menuJugarOAcabar(int lengua) {
        Scanner in = new Scanner(System.in);
        int opcion = 0;
        String[][] mensaje = {{"Choose an Option\nOption 1: Start a new game \nOption 2: Leave",
            "\nEscoje una opcion \nOpción 1: Empezar una nueva partida\nOpción 2: Salir",
            "\nEscull una opcio\nOpció 1: Començar una nova partida \nOpció 2: Sortir  "},
        {"Bye Byeee!!!", "Adiosssss!!!", "Adeuuuuuu!!!"}};
        do {
            opcion = filtroFormato(mensaje[0][lengua], lengua);
        } while (opcion != 1 && opcion != 2);
        if (opcion == 2) {
            System.out.println(mensaje[1][lengua]);
        }
        return opcion;
    }

    /**
     * Funcion para generar aleatoriamente un array de 7 letras, de las cuales 3
     * han de ser vocales y el resto consonantes.
     *
     * @param lengua
     * @return char[]
     */
    public static char[] generarArrayLletres(int lengua) {
        String[] tablaLetras = {"EAIONRTLSUDGBCMPFHVWYKJXQZ",
            "AEOISNLRUTDGCBMPHFVYQJÑXZ",
            "EAIRSNOTLUCDMBGPFVHJQZÇX"};
        char[] aux = new char[7];
        int v;
        do {
            v = 0;
            for (int i = 0; i < aux.length; i++) {
                int l = (int) (Math.random() * (tablaLetras[lengua].length()));
                aux[i] = tablaLetras[lengua].charAt(l);
                if (aux[i] == 'A' || aux[i] == 'E' || aux[i] == 'I' || aux[i] == 'O' || aux[i] == 'U') {
                    v++;
                }
            }
        } while (v != 3);
        for (int i = 0; i < aux.length; i++) {
            System.out.print(aux[i] + " ");
        }
        System.out.println("");
        return aux;
    }

    /**
     * Funcion para generar un array de palabras (diccionario) cargadas desde un archivo de texto
     *
     * @param lengua
     * @return String[]
     * @throws java.io.FileNotFoundException
     */
    public static String[] cargarPalabras(int lengua) throws FileNotFoundException {
        String[] nomFitxer = {"words.txt", "palabras.txt", "paraules.txt"};
        String[] palabras;
        try {
            Scanner lectura = new Scanner(new File(nomFitxer[lengua]));
            ArrayList<String> llista = new ArrayList();
            while (lectura.hasNext()) {
                String s = lectura.next().toUpperCase();
                llista.add(s);
            }
            palabras = llista.toArray(new String[0]);
            return palabras;
        } catch (FileNotFoundException e) {
            return palabras = new String[0];
        }
    }

    /**
     * Funcion que pide una palabra al usuario y la retorna siempre que esta cumpla con las condiciones.
     *
     * @param array
     * @param letras
     * @param lengua
     * @return String palabra
     */
    public static String solicitarPalabra(String[] array, char[] letras, int lengua) {
        Scanner entrada = new Scanner(System.in, "ISO-8859-1");
        String[][] mensajes = {{"Introduce a valid word", "The word does not exist", "You don't have enough letters"},
        {"Introduce una palabra valida", "La palabra no existe", "Te faltan letras"},
        {"Introdueix una paraula vàlida", "La paraula no existeix", "Et falten lletres"}};
        String aux;
        do {
            System.out.println(mensajes[lengua][0]);
            aux = entrada.next().toUpperCase();
            if (!estaEnDiccionari(array, aux)) {
                System.out.println(mensajes[lengua][1]);
            } else if (!paraulaValida(aux, letras)) {
                System.out.println(mensajes[lengua][2]);
            }
        } while (!estaEnDiccionari(array, aux) || !paraulaValida(aux, letras));
        return aux;
    }
    
    /**
     * Funcion que recibe un array de String (diccionario) y un String (palabra) y retorna true si la palabra se encuentra en el diccionario.
     *
     * @param array
     * @param paraula
     * @return boolean
     */
    public static boolean estaEnDiccionari(String[] array, String paraula) {
        boolean esta = false;
        paraula = paraula.toUpperCase();
        for (int i = 0; i < array.length && !esta; i++) {
            if (array[i].equalsIgnoreCase(paraula)) {
                esta = true;
            }
        }
        return esta;
    }

    /**
     * Funcion que recibe un String (palabra) y un array de char (letras)  
     * y devuelve true si es posible formar la palabra con las letras del array, de lo contrario devuelve un false.
     *
     * @param paraula
     * @param array
     * @return boolean
     */
    public static boolean paraulaValida(String paraula, char[] array) {
        boolean valida = paraula.length() < 8;

        char[] aux = new char[array.length];
        for (int i = 0; i < aux.length; i++) {
            aux[i] = array[i];
        }

        for (int i = 0; i < paraula.length() && valida; i++) {
            boolean find = false;
            for (int j = 0; j < aux.length && !find; j++) {
                if (paraula.charAt(i) == aux[j]) {
                    find = true;
                    aux[j] = '0';
                }
            }
            valida = find;
        }
        return valida;
    }

    /**
     * Funcion que recibe un String (palabra) y un int lengua y calcula la puntuacion de la palabra segun los varemos de puntos por letra de cada lengua.
     *
     * @param paraula
     * @param lengua
     * @return int puntuacion
     */
    public static int calcularPuntuacio(String paraula, int lengua) {
        int[] arrayPuntos = {1, 2, 3, 4, 5, 8, 10};
        int cont = 0;
        if (paraula.length() == 7) {
            cont = cont + 50;
        }
        String[][] tablaLetras = {{"EAIONRTLSU", "DG", "BCMP", "FHVWY", "K", "JX", "QZ"},
        {"AEOISNLRUT", "DG", "CBMP", "HFVY", "Q", "JÑX", "Z"},
        {"EAIRSNOTLU", "CDM", "BGP", "FV", "*", "HJQZ", "ÇX"}};
        boolean find;
        for (int i = 0; i < paraula.length(); i++) {
            find = false;
            for (int j = 0; j < tablaLetras[lengua].length && !find; j++) {
                for (int c = 0; c < tablaLetras[lengua][j].length(); c++) {
                    if (paraula.charAt(i) == tablaLetras[lengua][j].charAt(c)) {
                        cont = cont + arrayPuntos[j];
                        find = true;
                    }
                }
            }
        }
        return cont;
    }
    
    /**
     * Funcion que recive recibe un array de String (diccionario) y un array de char (letras)  
     * para encontrar la palabra con mas puntos del diccionario y que sea posible formarla con las letras del array
     *utilizamos las funciones paraulaValida y calcularPuntuacio
     * @param arrayParaules
     * @param arrayLletres
     * @param lengua
     * @return String millorParaula
     */
    public static String millorParaula(String[] arrayParaules, char[] arrayLletres, int lengua) {
        String[][] mensajes = {{"The computer word is: ", " And the puntuation for this word is: "},
        {"El ordenador ha usado la palabra: ", " Con una puntuacion de: "},
        {"L'ordinador ha fet servir la paraula:", " Amb una puntuació de:"}};
        int i = 0;
        while (!paraulaValida(arrayParaules[i], arrayLletres)) {
            i++;
        }
        int millorPuntuacio = calcularPuntuacio(arrayParaules[i], lengua);
        String millorParaula = arrayParaules[i];
        for (; i < arrayParaules.length; i++) {
            if (paraulaValida(arrayParaules[i], arrayLletres)) {
                int auxPuntuacio = calcularPuntuacio(arrayParaules[i], lengua);
                if (auxPuntuacio > millorPuntuacio) {
                    millorPuntuacio = auxPuntuacio;
                    millorParaula = arrayParaules[i];
                }
            }
        }
        System.out.println(mensajes[lengua][0] + millorParaula + mensajes[lengua][1] + calcularPuntuacio(millorParaula, lengua));
        return millorParaula;
    }

    /**
     * Funcion que recibe dos enteros (ountuaciones) y printa un mensaje segun el resultado de la comparacion de puntos
     *
     * @param a
     * @param b
     * @param lengua
     */
    public static void resultado(int a, int b, int lengua) {
        String[][] mensajes = {{"Same amount of points, Everyone wins !!!", "Congratulations You have won", "You have lost, you can try again", ": Player / Computer: "},
        {"Misma cantidad de puntos, Todos ganan!!!", "Felicidades Has ganado", "Has perdido, puedes volver a intentarlo", " :Jugador / Ordenador: "},
        {"Mateixa quantitat de punts, Tots guanyen !!!", "Felicitats Has guanyat", "Has perdut, pots tornar a intentar-ho", " : Jugador / Ordinador: "},};
        if (a == b) {
            System.out.println(mensajes[lengua][0]);
        } else {
            System.out.println((a > b) ? mensajes[lengua][1] : mensajes[lengua][2]);
        }
        System.out.println(a + mensajes[lengua][3] + b);
    }

}
