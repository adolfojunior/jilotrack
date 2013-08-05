package br.com.cubekode.jilotrack.store.h2;

import java.sql.SQLException;

import org.h2.tools.Console;

public class ServerWebConsole {

	public static void main(String[] args) {
		try {
			Console.main("-web", "-browser");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
