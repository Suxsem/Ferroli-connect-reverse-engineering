package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Matrix;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

@RequiresApi(14)
class ImageViewUtilsApi14 implements ImageViewUtilsImpl {
    ImageViewUtilsApi14() {
    }

    public void startAnimateTransform(ImageView imageView) {
        ImageView.ScaleType scaleType = imageView.getScaleType();
        imageView.setTag(C0139R.C0141id.save_scale_type, scaleType);
        if (scaleType == ImageView.ScaleType.MATRIX) {
            imageView.setTag(C0139R.C0141id.save_image_matrix, imageView.getImageMatrix());
        } else {
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
        }
        imageView.setImageMatrix(MatrixUtils.IDENTITY_MATRIX);
    }

    public void animateTransform(ImageView imageView, Matrix matrix) {
        imageView.setImageMatrix(matrix);
    }

    public void reserveEndAnimateTransform(final ImageView imageView, Animator animator) {
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                ImageView.ScaleType scaleType = (ImageView.ScaleType) imageView.getTag(C0139R.C0141id.save_scale_type);
                imageView.setScaleType(scaleType);
                imageView.setTag(C0139R.C0141id.save_scale_type, (Object) null);
                if (scaleType == ImageView.ScaleType.MATRIX) {
                    ImageView imageView = imageView;
                    imageView.setImageMatrix((Matrix) imageView.getTag(C0139R.C0141id.save_image_matrix));
                    imageView.setTag(C0139R.C0141id.save_image_matrix, (Object) null);
                }
                animator.removeListener(this);
            }
        });
    }
}
