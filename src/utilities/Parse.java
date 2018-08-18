package utilities;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.tiles.*;
import model.tiles.Corner.CornerTile;
import utilities.enumerations.Color;

/**
 * This utility class provide same Function to parse the record read from file.
 * 
 * @author Matteo Alesiani 
 */

public class Parse {
	private static final String CHAR = "-";
	
	private static final Function<List<Integer>, Rents> PARSING_RENTS = t -> {
						return new Rents(t);
					};
					
	public static final Function<String, BuildableImpl> PARSING_BUILDABLE_TILE_BOARD = value -> {
						List<String> record = getPlittingList(value);
						
						Integer positionTile = new Integer(record.get(0));
						Integer price = new Integer(record.get(1));
						Integer mortgage = new Integer(record.get(2));
						Rents rents = PARSING_RENTS.apply(record.subList(3, 9).stream()
																.map(Integer::new).collect(Collectors.toList()));
						Integer priceBuilding = new Integer(record.get(9));
						
						return new BuildableImpl(positionTile.intValue(), price.intValue(), mortgage.intValue(), rents, 
											 Color.valueOf(Color.class, record.get(10)), priceBuilding.intValue());
					};
						
	public static final Function<String, NotBuildableImpl> PARSING_NOTBUILDABLE_TILE_BOARD = value -> {
						List<String> record = getPlittingList(value);
						
						Integer positionTile = new Integer(record.get(0));
						Integer price = new Integer(record.get(1));
						Integer mortgage = new Integer(record.get(2));
						
						return new NotBuildableImpl(positionTile.intValue(), price.intValue(), mortgage.intValue(), Color.valueOf(Color.class, record.get(3)));
					};
					
	public static final Function<Integer, Corner> PARSING_CORNER = value -> {
						CornerTile cornerType = CornerTile.get(value);
						
						return new Corner(value*10, String.valueOf(cornerType).toLowerCase(), cornerType);
					};
					
	public static final BiConsumer<String, Stream<Tile>> PARSING_LOAD_MODEGAME = (record, stream) -> 
					stream.filter(t -> t.getPosition() == Integer.parseInt(getPlittingList(record).get(0)))
					.findFirst().get().setNameOf(getPlittingList(record).get(1));
					
	private static List<String> getPlittingList(final String value) {
					return Arrays.stream(value.split(CHAR)).collect(Collectors.toList());
	}
}
