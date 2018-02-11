interface RevisionInterface{
    id: string;
    title: string;
    status: string;
    review1: string;
    review2: string;
    hasLetter: boolean;
    review: ReviewInterface;
    reviews: ReviewInterface[];
}