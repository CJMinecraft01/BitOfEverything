package cjminecraft.api.pipes;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ItemPipeBlockstateGenerator {
	
	private static String[] connectorTypes;
	
	public ItemPipeBlockstateGenerator(String... connectorTypes) {
		ItemPipeBlockstateGenerator.connectorTypes = connectorTypes;
	}
	
	public ItemPipeBlockstateGenerator getInstance() {
		return this;
	}

	public static void generateBlockState(String unlocalizedName, String modid) {
		List<String> lines = new ArrayList<String>();

		int variantsListLength = (int) Math.pow(6, connectorTypes.length);
		System.out.println("Going to generate " + (variantsListLength + 4) + " lines of code for the blockstate");

		lines.add("{");
		lines.add("    \"variants\": {");

		boolean finished = false;

		for (int a = 0; a < connectorTypes.length; a++) {
			for (int b = 0; b < connectorTypes.length; b++) {
				for (int c = 0; c < connectorTypes.length; c++) {
					for (int d = 0; d < connectorTypes.length; d++) {
						for (int e = 0; e < connectorTypes.length; e++) {
							for (int f = 0; f < connectorTypes.length; f++) {
								if(a == connectorTypes.length) {
									lines.add("        \"down=" + connectorTypes[f] + ",east="
											+ connectorTypes[e] + ",north"
											+ connectorTypes[d] + ",south="
											+ connectorTypes[c] + ",up="
											+ connectorTypes[b] + ",west="
											+ connectorTypes[a] + "\" : { \"model\":\"" + modid
											+ ":" + unlocalizedName + "/d="
											+ connectorTypes[f] + ".e="
											+ connectorTypes[e] + ".n="
											+ connectorTypes[d] + ".s="
											+ connectorTypes[c] + ".u="
											+ connectorTypes[b] + ".w="
											+ connectorTypes[a] + "\" }");
									break;
								}else {
									lines.add("        \"down=" + connectorTypes[f] + ",east="
											+ connectorTypes[e] + ",north"
											+ connectorTypes[d] + ",south="
											+ connectorTypes[c] + ",up="
											+ connectorTypes[b] + ",west="
											+ connectorTypes[a] + "\" : { \"model\":\"" + modid
											+ ":" + unlocalizedName + "/d="
											+ connectorTypes[f] + ".e="
											+ connectorTypes[e] + ".n="
											+ connectorTypes[d] + ".s="
											+ connectorTypes[c] + ".u="
											+ connectorTypes[b] + ".w="
											+ connectorTypes[a] + "\" },");
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println(lines);

//		Path file = Paths.get("the-file-name.txt");
//		try {
//			Files.write(file, lines, Charset.forName("UTF-8"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
