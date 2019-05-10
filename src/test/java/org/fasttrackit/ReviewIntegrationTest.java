package org.fasttrackit;

import org.fasttrackit.Domain.Product;
import org.fasttrackit.Domain.Review;
import org.fasttrackit.Exception.ResourceNotFoundException;
import org.fasttrackit.Service.ReviewService;
import org.fasttrackit.Transfer.review.CreateReviewRequest;
import org.fasttrackit.steps.ProductSteps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;

public class ReviewIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testCreateReview_whenValidRequest_thenReturnReview() throws ResourceNotFoundException {
        Product product=productSteps.createProduct();

        CreateReviewRequest reviewRequest=new CreateReviewRequest();
        reviewRequest.setProductId(product.getId());
        reviewRequest.setContent("Super awesome");

        Review review = reviewService.createReview(reviewRequest);

        assertThat(review,notNullValue());
        assertThat(review.getId(),greaterThan(0L));
        assertThat(review.getProduct(),notNullValue());
        assertThat(review.getContent(),is(reviewRequest.getContent()));
    }
}
