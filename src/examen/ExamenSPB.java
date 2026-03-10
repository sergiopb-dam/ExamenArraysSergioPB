package examen;

import java.util.Scanner;

public class ExamenSPB {

	public static void main(String[] args) {
		// Examen de Arrays, String y Funciones - Sergio Peña Brenes 1º DAM - Programación
		Scanner sc = new Scanner(System.in);
		char[][] sala = new char[6][10];
		int opcion = 0;
		for (int i = 0; i < sala.length; i++) {
			for (int j = 0; j < sala[i].length; j++) {
				sala[i][j] = 'L';
			}
		}

		pintarSala(sala);

		do {
			System.out.println("\n--- MENÚ DE RESERVAS ---");
			System.out.println("1. Mostrar sala");
			System.out.println("2. Reservar asiento suelto");
			System.out.println("3. Reservar grupo en una fila");
			System.out.println("4. Confirmar reservas (R -> O)");
			System.out.println("5. Cancelar reservas (R -> L)");
			System.out.println("6. Estadísticas");
			System.out.println("7. Salir");
			System.out.print("Introduce la opción (1-7): ");
			opcion = sc.nextInt();
			sc.nextLine();
			switch (opcion) {
			case 1:
				pintarSala(sala);

				break;
			case 2:
				String posicion = "";

				do {
					System.out.println("Introduce la fila y la columna (x, y): ");
					posicion = sc.nextLine();
				} while (posicion.isBlank());

				posicion = posicion.trim().replace(" ", "");
				char[] posicionArray = posicion.toCharArray();
				if (reservarAsiento(sala, posicionArray[0], posicionArray[2])) {
					sala[posicionArray[0]][posicionArray[2]] = 'R';
				}
				System.out.println("Asiento Reservado.");
				break;
			case 3:
				int filaGrupo;
				int personas;
				do {
					System.out.println("Introduce la fila donde quieres buscar: ");
					filaGrupo = sc.nextInt();
					System.out.println("Introduce las personas que sois (0-6): ");
					personas = sc.nextInt();
				} while (filaGrupo < 0 && filaGrupo > 6 && personas > 0 && personas <= sala.length);
				// reservarGrupoEnFila(sala, filaGrupo, personas);
				System.out.println();
				System.out.println("Fila reservada.");
				System.out.println("---------------------");
				break;
			case 4:
				confirmarReservas(sala);
				System.out.println();
				System.out.println("Reservas confirmadas: ");
				System.out.println("---------------------");
				pintarSala(sala);
				break;
			case 5:
				cancelarReservas(sala);
				System.out.println();
				System.out.println("Reservas canceladas: ");
				System.out.println("---------------------");
				pintarSala(sala);
				break;
			case 6:
				mostrarEstadisticas(sala);
				break;
			case 7:
				System.out.println();
				System.out.println("Saliendo del Programa...");
				break;
			default:
				System.out.println();
				System.out.println("Opción incorrecta.");
				break;
			}
		} while (opcion != 7);

		sc.close();
	}

	/*
	 * Función para mostrar la sala por pantalla
	 * 
	 * @param sala
	 */
	static void pintarSala(char[][] sala) {
		System.out.println("Sala del teatro: ");
		System.out.println("----------------");
		for (int i = 0; i < sala.length; i++) {
			for (int j = 0; j < sala[i].length; j++) {
				System.out.print(sala[i][j]);
			}
			System.out.println();
		}
	} // Final de pintarSala

	/*
	 * Función para comprobar que la fila y la columna introducidas estén en la
	 * matriz
	 * 
	 * @param sala
	 * 
	 * @param fila
	 * 
	 * @param columna
	 * 
	 * @return
	 */
	static boolean esPosicionValida(char[][] sala, int fila, int columna) {
		boolean posicionValida = true;
		if (fila < 0 || fila >= sala.length || columna < 0 || columna >= sala.length) {
			posicionValida = false;
		}
		return posicionValida;
	} // Final de esPosicionValida

	/*
	 * Función para contar cuantos asientos hay en un estado concreto
	 * 
	 * @param sala
	 * 
	 * @param estado
	 * 
	 * @return
	 */
	static int contarEstado(char[][] sala, char estado) {
		int contador = 0;
		for (int i = 0; i < sala[i].length; i++) {
			for (int j = 0; j < sala[j].length; j++) {
				if (sala[i][j] == estado) {
					contador++;
				}
			}
		}
		return contador;
	} // Final de contarEstado

	/*
	 * Función para reservar asientos
	 * 
	 * @param sala
	 * 
	 * @param fila
	 * 
	 * @param columna
	 * 
	 * @return
	 */
	static boolean reservarAsiento(char[][] sala, int fila, int columna) {
		boolean validez = esPosicionValida(sala, fila, columna);
		if (validez == true) {
			sala[fila][columna] = 'R';
		} else {
			validez = false;
		}
		return validez;
	} // Final de reservarAsiento

	/*
	 * Función para reservar en grupo una fila
	 * 
	 * @param sala
	 * 
	 * @param fila
	 * 
	 * @param numeroPersonas
	 * 
	 * @return
	 */
	static int[] reservarGrupoEnFila(char[][] sala, int fila, int numeroPersonas) {
		int[] reserva = new int[2];
		int columnaInicio = 0;
		int finBloque = 0;
		int contador = 0;
		for (int i = 0; i < sala[i].length; i++) {
			if (sala[i][fila] == 'R') {
				contador = 0;
				columnaInicio = 0;
			}
			if (sala[i][fila] == 'L') {
				contador++;
				finBloque = sala[i][fila];
			}
		}

		if (contador == numeroPersonas) {
			for (int i = 0; i < sala[i].length; i++) {
				if (sala[i][fila] == 'L') {
					sala[i][fila] = 'R';
					reserva[0] = columnaInicio;
					reserva[1] = finBloque;
				}
			}
		} else {
			reserva = null;
		}

		return reserva;
	} // Final de reservarGrupoEnsFila

	/*
	 * Función para confirmar que se realizaron todas las reservas
	 * 
	 * @param sala
	 */
	static void confirmarReservas(char[][] sala) {
		for (int i = 0; i < sala.length; i++) {
			for (int j = 0; j < sala[i].length; j++) {
				if (sala[i][j] == 'R') {
					sala[i][j] = 'O';
				}
			}
		}
	} // Final de confirmarReservas

	/*
	 * Función para cancelar cualquier reserva
	 * 
	 * @param sala
	 */
	static void cancelarReservas(char[][] sala) {
		for (int i = 0; i < sala.length; i++) {
			for (int j = 0; j < sala[i].length; j++) {
				if (sala[i][j] == 'R') {
					sala[i][j] = 'L';
				}
			}
		}
	} // Final de cancelarReservas

	/*
	 * Función para mostrar las estadísticas del programa
	 * 
	 * @param sala
	 */
	static void mostrarEstadisticas(char[][] sala) {
		int numeroLibres = 0;
		int numeroOcupados = 0;
		int numeroReservados = 0;
		/*
		 * int contadorOcupados = 0; int contador = 0;
		 */
		for (int i = 0; i < sala.length; i++) {
			for (int j = 0; j < sala[i].length; j++) {
				if (sala[i][j] == 'R') {
					numeroReservados++;
				} else if (sala[i][j] == 'L') {
					numeroLibres++;
				} else {
					numeroOcupados++;
				}
			}
		}

		/*
		 * for (int i = 0; i < sala.length; i++) { for (int j = 0; j < sala[i].length;
		 * j++) { if (sala[i][j] == 'O') { contador++; if (contadorOcupados > 0) {
		 * contadorOcupados++; } } } }
		 */
		System.out.println("Mostrando estadísticas: ");
		System.out.println("______________________________");
		System.out.println("Hay " + numeroLibres + " asientos libres.");
		System.out.println("Hay " + numeroOcupados + " asientos ocupados.");
		System.out.println("Hay " + numeroReservados + " asientos reservados.");
		System.out.println();
	} // Final de mostrarEstadisticas

}
