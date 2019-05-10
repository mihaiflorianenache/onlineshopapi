package org.fasttrackit.Service;

import org.fasttrackit.Domain.Product;
import org.fasttrackit.Domain.Review;
import org.fasttrackit.Exception.ResourceNotFoundException;
import org.fasttrackit.Persistence.ReviewRepository;
import org.fasttrackit.Transfer.review.CreateReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,ProductService productService){
        this.reviewRepository=reviewRepository;
        this.productService=productService;
    }

    @Transactional
    public Review createReview(CreateReviewRequest request) throws ResourceNotFoundException {
        Product product=productService.getProduct(request.getProductId());
        Review review=new Review();
        review.setContent(request.getContent());
        review.setProduct(product);

        return reviewRepository.save(review);
    }
}
