/*
 * The compilation of software known as V4L4J is distributed under the
 * following terms:
 *
 * Copyright (c) 2015 Christopher Friedt. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package org.v4l4j;

import java.util.*;

import com.sun.jna.*;

public class V4L4JFrameIntervalEnum extends Structure {

	public static class ByReference extends V4L4JFrameIntervalEnum implements Structure.ByReference {};

	public static class FrameInterval extends Union {
		public static class ByReference extends FrameInterval implements Union.ByReference {};
		
		public V4L4JFract discrete;
		public V4L4JFrameIntervalStepwise stepwise;
	}
	
	public int index;
	public int pixel_format;
	public int width;
	public int height;
	public int type;
	public FrameInterval frame_interval;
	public int[] reserved = new int[ 2 ];
	
	@Override
	public String toString() {
		return getClass().getName() + '@' + Integer.toHexString(hashCode()) + ", " +
			"index: " + index +
			", " +
			"pixel_format: " + Integer.toHexString( pixel_format ) +
			", " +
			"width: " + width +
			", " +
			"height: " + height +
			", " +
			"type: " + Integer.toHexString( type ) +
			", " +
			"frame_interval: " + frame_interval +
			"";
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List getFieldOrder() {
		return Arrays.asList( new String[] { "index", "pixel_format", "width", "height", "type", "frame_interval", "reserved", } );
	}

	@Override
	public void write() {
		
		final int V4L2_FRMIVAL_TYPE_DISCRETE = 1;
		final int V4L2_FRMIVAL_TYPE_CONTINUOUS = 2;
		final int V4L2_FRMIVAL_TYPE_STEPWISE = 3;
		
        switch( type ) {

        case V4L2_FRMIVAL_TYPE_STEPWISE:
            frame_interval.setType( V4L4JFrameIntervalStepwise.class );
            break;

        case V4L2_FRMIVAL_TYPE_DISCRETE:
        case V4L2_FRMIVAL_TYPE_CONTINUOUS:
        default:
            frame_interval.setType( V4L4JFract.class );
            break;
        }
        super.write();
	}
	
	@Override
    public void read() {
		
		final int V4L2_FRMIVAL_TYPE_DISCRETE = 1;
		final int V4L2_FRMIVAL_TYPE_CONTINUOUS = 2;
		final int V4L2_FRMIVAL_TYPE_STEPWISE = 3;
		
        super.read();
        switch( type ) {

        case V4L2_FRMIVAL_TYPE_STEPWISE:
            frame_interval.setType( V4L4JFrameIntervalStepwise.class );
            break;

        case V4L2_FRMIVAL_TYPE_DISCRETE:
        case V4L2_FRMIVAL_TYPE_CONTINUOUS:
        default:
            frame_interval.setType( V4L4JFract.class );
            break;
        }
        frame_interval.read();
	}
}
