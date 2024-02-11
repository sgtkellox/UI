package modelCollection;

import java.io.IOException;
import java.nio.file.Paths;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.YoloV5Translator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.Pipeline;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;

public class FastRCNNPytorch {
	
	public FastRCNNPytorch() throws TranslateException, ModelNotFoundException, MalformedModelException, IOException {
		String imgPath = "C:\\Users\\felix\\Desktop\\FinalTraining\\test\\IMG_0344.jpg";
		String modelPath = "C:\\Users\\felix\\Desktop\\FinalTraining\\pytorchResult\\traced_resnet_model.pt";
			
		Pipeline pipeline = new Pipeline();
		pipeline.add(new Resize(640, 640));
		pipeline.add(new ToTensor());
	
		
		Translator<Image, DetectedObjects> translator = YoloV5Translator.builder().setPipeline(pipeline).optSynsetArtifactName("synset.txt")
				.optThreshold(0.5f)
				.build();
	
		
		
		
		Criteria<Image, DetectedObjects> criteria =
                Criteria.builder()
                        .optApplication(Application.CV.OBJECT_DETECTION)
                        .setTypes(Image.class, DetectedObjects.class)
                        .optModelPath(Paths.get(modelPath))
                        .optTranslator(translator)
                        .optProgress(new ProgressBar())
                        .build();

		ZooModel<Image, DetectedObjects> model =  criteria.loadModel();
		
		Image img = ImageFactory.getInstance().fromFile(Paths.get(imgPath));
		img.getWrappedImage();
		
		
		Predictor<Image, DetectedObjects> predictor = model.newPredictor();
		DetectedObjects Detected_dents = predictor.predict(img);
		System.out.println(Detected_dents);
	}

}
