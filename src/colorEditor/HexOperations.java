package colorEditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HexOperations {

	/**
	 * Create the application.
	 */
	public static String[][] getHexArray(String path) {

		// Reading Hex file
		String filePath = path;

		String hexValues = "";
		try {
			hexValues = readHexFile(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(hexValues);

		// Limita o tamanho do array a 16

		String[] organizedArray = reorganizeHex(hexValues);
		if (organizedArray.length > 16) {
			System.out.println("The array has " + organizedArray.length + " items. It will be truncated to 16 items.");
			organizedArray = Arrays.copyOf(organizedArray, 16);
		}

		// Gerando uma matrix com as cores
		String[][] colorMatrix = new String[4][4];
		int hexIndex = 0;
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				colorMatrix[i][j] = organizedArray[hexIndex];
				hexIndex++;
			}
		}
		
		// Imprimindo a paleta de cores
		System.out.println("This is your matrix:");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(colorMatrix[i][j] + " ");
			}
			System.out.println();
		}

		return colorMatrix;
	}

	public static String readHexFile(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		StringBuilder hexString = new StringBuilder();

		int byteValue;
		while ((byteValue = fis.read()) != -1) {
			// Converte o byte para uma string hexadecimal
			hexString.append(String.format("%02X ", byteValue));
		}

		fis.close();
		return hexString.toString().trim(); // Remove o espaço extra no final
	}

	public static String[] reorganizeHex(String hexString) {
		// Remove espaços em branco e divide a string em um array de pares
		String[] hexPairs = hexString.split(" ");
		List<String> result = new ArrayList<>();

		// Agrupa os pares em blocos de 3 (6 dígitos)
		StringBuilder currentBlock = new StringBuilder();
		for (int i = 0; i < hexPairs.length; i++) {
			currentBlock.append(hexPairs[i]);
			// Quando temos 3 pares (6 dígitos) obtemos uma cor em código hexadecimal,
			// que será adicionada ao resultado.
			if ((i + 1) % 3 == 0) {
				result.add("#" + currentBlock.toString());
				currentBlock.setLength(0); // Limpa o StringBuilder para o próximo bloco
			}
		}

		// Se houver pares restantes, adiciona ao resultado
		if (currentBlock.length() > 0) {
			result.add(currentBlock.toString());
		}

		// Converte a lista para um array
		return result.toArray(new String[0]);
	}
}
