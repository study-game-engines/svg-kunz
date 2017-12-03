/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.svg.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultRawSVGTransformer implements RawSVGTransformer
{
	public static interface SupplierConsumer extends Supplier<Double>, Consumer<Double>
	{
	}

	private Collection<SupplierConsumer>	supplierConsumersLocationX;
	private Collection<SupplierConsumer>	supplierConsumersLocationY;
	private Collection<SupplierConsumer>	supplierConsumersWidth;
	private Collection<SupplierConsumer>	supplierConsumersHeight;
	private Collection<SupplierConsumer>	supplierConsumersRadius;
	private RawSVGElement					rawSVGElement;

	public DefaultRawSVGTransformer(RawSVGElement rawSVGElement, SupplierConsumer supplierConsumersX, SupplierConsumer supplierConsumersY)
	{
		this(rawSVGElement, Arrays.asList(supplierConsumersX), Arrays.asList(supplierConsumersY), Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());
	}

	public DefaultRawSVGTransformer(RawSVGElement rawSVGElement, SupplierConsumer supplierConsumersLocationX, SupplierConsumer supplierConsumersLocationY,
									SupplierConsumer supplierConsumersWidth, SupplierConsumer supplierConsumersHeight)
	{
		this(rawSVGElement, Arrays.asList(supplierConsumersLocationX), Arrays.asList(supplierConsumersLocationY), Collections.emptyList(),
				Arrays.asList(supplierConsumersWidth), Arrays.asList(supplierConsumersHeight));
	}

	public DefaultRawSVGTransformer(RawSVGElement rawSVGElement, SupplierConsumer supplierConsumersX, SupplierConsumer supplierConsumersY,
									SupplierConsumer supplierConsumersXY)
	{
		this(rawSVGElement, Arrays.asList(supplierConsumersX), Arrays.asList(supplierConsumersY), Arrays.asList(supplierConsumersXY), Collections.emptyList(),
				Collections.emptyList());
	}

	public DefaultRawSVGTransformer(RawSVGElement rawSVGElement, Collection<SupplierConsumer> supplierConsumersLocationX,
									Collection<SupplierConsumer> supplierConsumersLocationY, Collection<SupplierConsumer> supplierConsumersRadius,
									Collection<SupplierConsumer> supplierConsumersWidth, Collection<SupplierConsumer> supplierConsumersHeight)
	{
		super();
		this.rawSVGElement = rawSVGElement;
		this.supplierConsumersLocationX = supplierConsumersLocationX;
		this.supplierConsumersLocationY = supplierConsumersLocationY;
		this.supplierConsumersRadius = supplierConsumersRadius;
		this.supplierConsumersWidth = supplierConsumersWidth;
		this.supplierConsumersHeight = supplierConsumersHeight;
	}

	@Override
	public RawSVGElement translate(double x, double y)
	{
		this.supplierConsumersLocationX.forEach(cs -> cs.accept(cs.get() + x));
		this.supplierConsumersLocationY.forEach(cs -> cs.accept(cs.get() + y));
		return this.rawSVGElement;
	}

	@Override
	public RawSVGElement scale(double scaleX, double scaleY)
	{
		this.supplierConsumersLocationX.forEach(cs -> cs.accept(cs.get() * scaleX));
		this.supplierConsumersLocationY.forEach(cs -> cs.accept(cs.get() * scaleY));
		this.supplierConsumersWidth.forEach(cs -> cs.accept(cs.get() * scaleX));
		this.supplierConsumersHeight.forEach(cs -> cs.accept(cs.get() * scaleY));
		this.supplierConsumersRadius.forEach(cs -> cs.accept(cs.get() * Math.sqrt(scaleX * scaleY)));
		return this.rawSVGElement;
	}

	@Override
	public String toString()
	{
		return "DefaultRawSVGTransformer [supplierConsumersX=" + this.supplierConsumersLocationX + ", supplierConsumersY=" + this.supplierConsumersLocationY
				+ ", rawSVGElement=" + this.rawSVGElement + "]";
	}

}
