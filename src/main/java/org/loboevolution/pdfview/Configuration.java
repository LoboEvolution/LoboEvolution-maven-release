package org.loboevolution.pdfview;

/**
 * Since there is no context that is passed between the various classes that
 * perform the pdf parsing and rendering, we introduce this class to at least
 * globally configure PDFRenderer.
 *
 * Typically you would configure the global instance before using any other
 * PDFRenderer API.
 *
 * @author utente
 * @version $Id: $Id
 */
public class Configuration {
	private static Configuration INSTANCE;

    /** whether grey scale images will be converted to ARGB */
    private boolean convertGreyscaleImagesToArgb = true;
    /** threshold in pixels after which images are rendered in chunks (disabled by default) */
    private int thresholdForBandedImageRendering = 0;
    /** whether color convert op should be used or not for parsing an image*/
    private boolean avoidColorConvertOp = false;
    /** Use blur before image resize to enhance the result (Antialias) **/
    private boolean useBlurResizingForImages = true;
    
    /** Print signature fields on pdf **/
    private boolean printSigantureFields = true;
    /** Print stamp annotations on pdf **/
    private boolean printStampAnnotations = true;
    /** Print widget annotations on pdf **/
    private boolean printWidgetAnnotations = true;
    /** Print freetext annotations on pdf **/
    private boolean printFreetextAnnotations = true;
    /** Print link annotations on pdf **/
    private boolean printLinkAnnotations = true;
    
	/**
	 * <p>getInstance.</p>
	 *
	 * @return a {@link org.loboevolution.pdfview.Configuration} object.
	 */
	public static synchronized Configuration getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Configuration();
		}
		return INSTANCE;
	}

	/**
	 * Enables or disables the conversion of greyscale images to ARGB.
	 * Disabling this may have a lower memory overhead with high resolution
	 * (e.g. scanned) images. 
	 *
	 * Enabled by default.
	 *
	 * @param aFlag whether greyscale images shall be converted to ARGB.
	 */
	public void setConvertGreyscaleImagesToArgb(boolean aFlag) {
		convertGreyscaleImagesToArgb = aFlag;
	}

	/**
	 * Returns true if greyscale images will be converted to ARGB
	 *
	 * @return a boolean.
	 */
	public boolean isConvertGreyscaleImagesToArgb() {
		return convertGreyscaleImagesToArgb;
	}

	/**
	 * If an image is higher than the given size (in pixels) then
	 * the image will be rendered in chunks, rather than as one big image.
	 * This may lead to lower memory consumption for e.g. scanned PDFs with
	 * large images.
	 *
	 * @param aSize the height threshold at which to enable banded image rendering
	 */
	public void setThresholdForBandedImageRendering(int aSize) {
		thresholdForBandedImageRendering = aSize;
	}

	/**
	 * Returns the image height threshold at which to enable banded image rendering.
	 *
	 * @return the threshold value
	 */
	public int getThresholdForBandedImageRendering() {
		return thresholdForBandedImageRendering;
	}

    /**
     * Is the color converting op switched on or off?
     *
     * @return - the usage of this color convert op
     */
    public boolean isAvoidColorConvertOp() {
        return avoidColorConvertOp;
    }
    
    /**
     * Set this to false to switch off the
     * use of this color convert op which may segfault on some platforms
     * due to a variety of problems related to thread safety and
     * the native cmm library underlying this conversion op, e.g.,
     *
     * If the system is bug-free, though, this does make use
     * of native libraries and sees a not insignificant speed-up,
     * though it's still not exactly fast. If we don't run this op
     * now, it's performed at some later stage, but without using
     * the native code
     *
     * @param avoidColorConvertOp a boolean.
     */
    public void setAvoidColorConvertOp(boolean avoidColorConvertOp) {
        this.avoidColorConvertOp = avoidColorConvertOp;
    }

    /**
     * Use blur before image resize to enhance the result (Antialias)?
     *
     * @return the useBlurResizingForImages
     */
    public boolean isUseBlurResizingForImages() {
        return useBlurResizingForImages;
    }

    /**
     * Use blur before image resize to enhance the result (Antialias)
     *
     * @param useBlurResizingForImages a boolean.
     */
    public void setUseBlurResizingForImages(boolean useBlurResizingForImages) {
        this.useBlurResizingForImages = useBlurResizingForImages;
    }
    
    /**
     * Print signature fields on pdf
     *
     * @param printSignatureFields a boolean.
     */
    public void setPrintSignatureFields(boolean printSignatureFields) {
    	this.printSigantureFields = printSignatureFields;
    }
    
    /**
     * <p>isPrintSignatureFields.</p>
     *
     * @return true if signature fields will be printed on pdf
     */
    public boolean isPrintSignatureFields() {
    	return this.printSigantureFields;
    }

    /**
     * Print stamp annotations on pdf
     *
     * @param printStampAnnotations a boolean.
     */
    public void setPrintStampAnnotations(boolean printStampAnnotations) {
    	this.printStampAnnotations = printStampAnnotations;
    }
    
    /**
     * <p>isPrintStampAnnotations.</p>
     *
     * @return true if stamp annotations will be printed on pdf
     */
    public boolean isPrintStampAnnotations() {
    	return this.printStampAnnotations;
    }
    
    /**
     * Print widget annotations on pdf
     *
     * @param printWidgetAnnotations a boolean.
     */
    public void setPrintWidgetAnnotations(boolean printWidgetAnnotations) {
    	this.printWidgetAnnotations = printWidgetAnnotations;
    }
    
    /**
     * <p>isPrintWidgetAnnotations.</p>
     *
     * @return true if widget annotations will be printed on pdf
     */
    public boolean isPrintWidgetAnnotations() {
		return this.printWidgetAnnotations;
	}
    
    /**
     * Print freetext annotations on pdf
     *
     * @param printFreetextAnnotations a boolean.
     */
    public void setPrintFreetextAnnotations(boolean printFreetextAnnotations) {
    	this.printFreetextAnnotations = printFreetextAnnotations;
    }
    
    /**
     * <p>isPrintFreetextAnnotations.</p>
     *
     * @return true if freetext annotations will be printed on pdf
     */
    public boolean isPrintFreetextAnnotations() {
    	return this.printFreetextAnnotations;
    }
    
    /**
     * Print link annotations on pdf
     *
     * @param printLinkAnnotations a boolean.
     */
    public void setPrintLinkAnnotations(boolean printLinkAnnotations) {
    	this.printLinkAnnotations = printLinkAnnotations;
    }
    
    /**
     * <p>isPrintLinkAnnotations.</p>
     *
     * @return true if link annotations will be printed on pdf
     */
    public boolean isPrintLinkAnnotations() {
    	return this.printLinkAnnotations;
    }
}
