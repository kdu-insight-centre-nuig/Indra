package org.lambda3.indra.service.test;

/*-
 * ==========================License-Start=============================
 * Indra Web Service Module
 * --------------------------------------------------------------------
 * Copyright (C) 2016 - 2017 Lambda^3
 * --------------------------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ==========================License-End===============================
 */

import org.lambda3.indra.client.DenseVectorResponse;
import org.lambda3.indra.client.SparseVectorResponse;
import org.lambda3.indra.client.VectorRequest;
import org.lambda3.indra.client.VectorResponse;
import org.lambda3.indra.core.IndraDriver;
import org.lambda3.indra.core.VectorSpaceFactory;
import org.lambda3.indra.core.test.IndraDriverTest;
import org.lambda3.indra.core.translation.IndraTranslatorFactory;
import org.lambda3.indra.service.impl.VectorResourceImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VectorResourceImplTest {

    @Test
    public void generalTest() {
        VectorSpaceFactory vectorSpaceFactory = IndraDriverTest.createVectorSpaceFactor();
        IndraTranslatorFactory translatorFactory = IndraDriverTest.createIndraTranslatorFactory();
        IndraDriver driver = new IndraDriver(vectorSpaceFactory, translatorFactory);
        VectorResourceImpl impl = new VectorResourceImpl(driver);

        List<String> terms = Arrays.asList("love", "mother", "father");
        VectorRequest request = new VectorRequest().language("EN").mt(false).terms(terms);
        VectorResponse response = impl.getVector(request);
        Assert.assertNotNull(response);
        Assert.assertTrue(response instanceof DenseVectorResponse);

        Map<String, double[]> vectors = ((DenseVectorResponse) response).getTerms();
        Assert.assertNotNull(vectors);
        Assert.assertEquals(vectors.size(), 3);

        for (double[] array : vectors.values()) {
            Assert.assertTrue(array.length > 0);
        }

        Assert.assertFalse(response instanceof SparseVectorResponse);
    }
}
