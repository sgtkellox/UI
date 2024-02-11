package modelCollection;

import java.nio.file.*;
import java.util.ArrayList;
import java.io.File;
import ai.djl.*;
import ai.djl.inference.*;
import ai.djl.modality.cv.*;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.DetectedObjects.DetectedObject;
import ai.djl.modality.cv.transform.*;
import ai.djl.modality.cv.translator.*;
import ai.djl.repository.zoo.*;
import ai.djl.translate.*;
import javafx.concurrent.Task;
import yolointerface.Detection;
import yolointerface.ImageContainer;
import yolointerface.ImageType;
import ai.djl.training.util.*;

public class YoloPytorchRGB extends Task<Object> {

	Predictor<Image, DetectedObjects> predictor;

	ImageContainer container = ImageContainer.instance();
	
	private String modelPath = "C:\\AI\\yolov5\\yolov5\\runs\\train\\exp10\\weights\\best.torchscript";

	public YoloPytorchRGB() {

	}

	@Override
	protected Object call() throws Exception {
		Pipeline pipeline = new Pipeline();
		pipeline.add(new Resize(640, 640));
		pipeline.add(new ToTensor());

		Translator<Image, DetectedObjects> translator = YoloV5Translator.builder().setPipeline(pipeline)
				.optSynsetArtifactName("synset.txt").optThreshold(0.5f).build();

		Criteria<Image, DetectedObjects> criteria = Criteria.builder().optApplication(Application.CV.OBJECT_DETECTION)
				.setTypes(Image.class, DetectedObjects.class)
				.optModelPath(Paths.get(modelPath))
				.optTranslator(translator).optDevice(Device.gpu()).optProgress(new ProgressBar()).build();

		ZooModel<Image, DetectedObjects> model = criteria.loadModel();

		predictor = model.newPredictor();

		int count = ImageContainer.getRgbImages().size();

		int index = 0;

		for (File file : ImageContainer.getRgbImages()) {
			ArrayList<Detection> detections = new ArrayList<Detection>();
			Image img = ImageFactory.getInstance().fromFile(Paths.get(file.getAbsolutePath()));
			img.getWrappedImage();
			DetectedObjects detectedTrees = predictor.predict(img);
			for (int i = 0; i < detectedTrees.getNumberOfObjects(); i++) {
				DetectedObject detectionInDjlFormat = detectedTrees.item(i);
				Detection detection = new Detection(detectionInDjlFormat.getBoundingBox().getBounds(),
						(int) (detectionInDjlFormat.getProbability() * 100), ImageType.RGB);
				detections.add(detection);
			}
			ImageContainer.getRGBDetections().get(file).addAll(detections);
			index++;
			this.updateProgress(index, count);
			
		}
		return null;
	}

}
