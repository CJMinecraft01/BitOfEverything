package cjminecraft.bitofeverything.util;

import java.util.Collection;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.properties.PropertyHelper;

/**
 * Used purely for the slabs. Every slab requires a variant so this is a great
 * class to use if you have no variant
 * 
 * @author CJMinecraft
 *
 */
public class DummyBlockProperty extends PropertyHelper<Boolean> {

	/**
	 * Initialise the dummy property
	 * 
	 * @param name
	 *            The name of the property
	 */
	protected DummyBlockProperty(String name) {
		super(name, Boolean.class);
	}

	/**
	 * Say that we only allow false as a valid value
	 */
	@Override
	public Collection<Boolean> getAllowedValues() {
		return ImmutableSet.<Boolean>of(Boolean.valueOf(false));
	}

	/**
	 * Parse a value. Will always be false
	 */
	@Override
	public Optional<Boolean> parseValue(String value) {
		return Optional.<Boolean>of(Boolean.valueOf(false));
	}

	/**
	 * The name from the value. Will always be false
	 */
	@Override
	public String getName(Boolean value) {
		return "false";
	}

	/**
	 * Create a new dummy property
	 * 
	 * @param name
	 *            The name of the property
	 * @return the new dummy property
	 */
	public static DummyBlockProperty create(String name) {
		return new DummyBlockProperty(name);
	}

}
