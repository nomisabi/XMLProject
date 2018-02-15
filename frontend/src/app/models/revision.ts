interface RevisionInterface{
    id: string;
    title: string;
    status: string;
    review1: string;
    review2: string;
    hasLetter: boolean;
    hasReview?: boolean;
    review: ReviewInterface;
    reviews: ReviewInterface[];
    flag?: boolean;
}