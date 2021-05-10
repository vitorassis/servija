package servija.helper;

public class CPF {
	public static String removeMask(String cpf) {
		return cpf.replaceAll("\\D", "");
	}
}
