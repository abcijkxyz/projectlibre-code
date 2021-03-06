/*******************************************************************************
 * The contents of this file are subject to the Common Public Attribution License 
 * Version 1.0 (the "License"); you may not use this file except in compliance with 
 * the License. You may obtain a copy of the License at 
 * http://www.projectlibre.com/license . The License is based on the Mozilla Public 
 * License Version 1.1 but Sections 14 and 15 have been added to cover use of 
 * software over a computer network and provide for limited attribution for the 
 * Original Developer. In addition, Exhibit A has been modified to be consistent 
 * with Exhibit B. 
 *
 * Software distributed under the License is distributed on an "AS IS" basis, 
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the 
 * specific language governing rights and limitations under the License. The 
 * Original Code is ProjectLibre. The Original Developer is the Initial Developer 
 * and is ProjectLibre Inc. All portions of the code written by ProjectLibre are 
 * Copyright (c) 2012-2019. All Rights Reserved. All portions of the code written by 
 * ProjectLibre are Copyright (c) 2012-2019. All Rights Reserved. Contributor 
 * ProjectLibre, Inc.
 *
 * Alternatively, the contents of this file may be used under the terms of the 
 * ProjectLibre End-User License Agreement (the ProjectLibre License) in which case 
 * the provisions of the ProjectLibre License are applicable instead of those above. 
 * If you wish to allow use of your version of this file only under the terms of the 
 * ProjectLibre License and not to allow others to use your version of this file 
 * under the CPAL, indicate your decision by deleting the provisions above and 
 * replace them with the notice and other provisions required by the ProjectLibre 
 * License. If you do not delete the provisions above, a recipient may use your 
 * version of this file under either the CPAL or the ProjectLibre Licenses. 
 *
 *
 * [NOTE: The text of this Exhibit A may differ slightly from the text of the notices 
 * in the Source Code files of the Original Code. You should use the text of this 
 * Exhibit A rather than the text found in the Original Code Source Code for Your 
 * Modifications.] 
 *
 * EXHIBIT B. Attribution Information for ProjectLibre required
 *
 * Attribution Copyright Notice: Copyright (c) 2012-2019, ProjectLibre, Inc.
 * Attribution Phrase (not exceeding 10 words): 
 * ProjectLibre, open source project management software.
 * Attribution URL: http://www.projectlibre.com
 * Graphic Image as provided in the Covered Code as file: projectlibre-logo.png with 
 * alternatives listed on http://www.projectlibre.com/logo 
 *
 * Display of Attribution Information is required in Larger Works which are defined 
 * in the CPAL as a work which combines Covered Code or portions thereof with code 
 * not governed by the terms of the CPAL. However, in addition to the other notice 
 * obligations, all copies of the Covered Code in Executable and Source Code form 
 * distributed must, as a form of attribution of the original author, include on 
 * each user interface screen the "ProjectLibre" logo visible to all users. 
 * The ProjectLibre logo should be located horizontally aligned with the menu bar 
 * and left justified on the top left of the screen adjacent to the File menu. The 
 * logo must be at least 144 x 31 pixels. When users click on the "ProjectLibre" 
 * logo it must direct them back to http://www.projectlibre.com. 
 *******************************************************************************/
package com.projectlibre1.graphic.configuration;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

import com.projectlibre1.graphic.configuration.shape.Colors;
import com.projectlibre1.graphic.configuration.shape.PredefinedPaint;
import com.projectlibre1.graphic.configuration.shape.PredefinedShape;
import com.projectlibre1.graphic.configuration.shape.PredefinedStroke;

public class TexturedShape {
	PredefinedShape shape = null;

	Color color = null;

	Paint paint = null;

	Stroke stroke = null;

	String paintName = null;

	String colorName = null;

	String strokeName = null;

	String shapeName = null;

	double shapeScaleX = 1.0d;

	double shapeScaleY = 1.0d;

	public TexturedShape() {
	}

	void build() {
		shape = PredefinedShape.find(shapeName);
		color = Colors.findColor(colorName);
		stroke = PredefinedStroke.find(strokeName);
		paint = new PredefinedPaint(PredefinedPaint.find(paintName), color, Colors.findColor("WHITE"));
	}

	public void setShapeName(String shapeName) {
		this.shapeName = shapeName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public void setStrokeName(String strokeName) {
		this.strokeName = strokeName;
	}

	public void setPaintName(String paintName) {
		this.paintName = paintName;
	}

	public Color getColor() {
		return color;
	}

	public Paint getPaint() {
		return paint;
	}

	public PredefinedShape getShape() {
		return shape;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public void setShape(PredefinedShape shape) {
		this.shape = shape;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public Shape draw(Graphics2D g2, double w, double h, double dw, double dh, boolean texture) {
		return draw(g2, w, h, dw, dh, null, texture);
	}

	public double getShapeScaleX() {
		return shapeScaleX;
	}

	public void setShapeScaleX(double shapeScaleX) {
		this.shapeScaleX = shapeScaleX;
	}

	public double getShapeScaleY() {
		return shapeScaleY;
	}

	public void setShapeScaleY(double shapeScaleY) {
		this.shapeScaleY = shapeScaleY;
	}

	public Shape draw(Graphics2D g2, double dw, double dh, AffineTransform transform, boolean texture) {
		return draw(g2, shapeScaleX, shapeScaleY, dw, dh, transform, texture);
	}

	public GeneralPath toGeneralPath(double w, double h, double dw, double dh, AffineTransform transform) {
		GeneralPath theShape = getShape().toGeneralPath(w - 1, h, dw, dh);// -1
																			// to
																			// have
																			// edge
																			// inside
																			// bounds;
		if (transform != null)
			theShape.transform(transform);
		return theShape;
	}

	public Shape draw(Graphics2D g2, double w, double h, double dw, double dh, AffineTransform transform, boolean texture) {
		Shape theShape = toGeneralPath(w, h, dw, dh, transform);
		paintShape(g2, theShape, texture);
		return theShape;
	}

	public void paintShape(Graphics2D g2, Shape theShape, boolean texture) {
		Stroke oldStroke = null;
		Paint oldPaint = null;
		Color oldColor = null;

		Paint myPaint = getPaint(); // can be null
		Stroke myStroke = getStroke();
		if (myPaint == null) { // if no paint, then just set color and draw
								// using stroke
			oldColor = g2.getColor();
			g2.setColor(getColor()); // no paint, so just set color
			if (myStroke != PredefinedStroke.SOLID) {
				oldStroke = g2.getStroke();
				g2.setStroke(myStroke);
			}
			g2.draw(theShape);
		} else { // use paint
			oldPaint = g2.getPaint();
			applyPaint(g2, texture);
			g2.fill(theShape);
			if (myStroke != PredefinedStroke.SOLID) { // if also specified a
														// stroke, use it too
				oldColor = g2.getColor();
				g2.setColor(getColor());
				oldStroke = g2.getStroke();
				g2.setStroke(myStroke);
				g2.draw(theShape);
			}
		}
		if (oldColor != null)
			g2.setColor(oldColor);
		if (oldPaint != null)
			g2.setPaint(oldPaint);
		if (oldStroke != null)
			g2.setStroke(oldStroke);
	}

	protected void applyPaint(Graphics2D g2, boolean texture) {
		// if ("SVGGraphics2D".equals(g2.getClass().getSimpleName()))
		if (texture)
			g2.setPaint(paint); // the paint already has the color set
		else {
			if (paint instanceof PredefinedPaint) {
				PredefinedPaint p = (PredefinedPaint) paint;
				p.applyPaint(g2, texture);
			} else
				g2.setColor(getColor());
		}

	}

}
